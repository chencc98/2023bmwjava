package com.example.pipeline.inbound;


import java.sql.SQLException;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.example.pipeline.entity.FeedInbound;
import com.example.pipeline.utils.MyUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PullDatabaseWorker extends GenericPullMethod {
    private FeedInbound inbound;
    private PullDatabaseAttr attr;

    @Override
    public void setupInbound(FeedInbound inbound) {
        this.inbound = inbound;
        try {
            this.attr = MyUtils.strToPullDatabaseAttr(inbound.getPullAttr());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    @Override
    public void startWork() {
        NamedParameterJdbcTemplate jdbcTemplate = null;
        try {
            jdbcTemplate  = getJdbcTemplate();
            MapSqlParameterSource source = getParamSource();
            List<Map<String, Object>> data = jdbcTemplate.queryForList(this.attr.getSqlToRun(), source);
            if (data.isEmpty()) {
                log.info("no date for inbound: " + this.inbound.getUuid());
                return;
            }
            String filepath = saveDataToTemp(data);
            log.info("save data to temp file:" + filepath);
            boolean isSame = compareWithPrevious(this.inbound, filepath);
            if (isSame) {
                log.info("the same content of data. skip...");
                //remove temp?
                return;
            }
            String batchId = genBatchId();
            log.info("try to copy to workarea ...");
            copyToWorkArea(this.inbound, filepath, batchId);
            
            createBatchRecord(this.inbound, batchId);
            moveToArchive(inbound, filepath);
            log.info("work is done");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private NamedParameterJdbcTemplate getJdbcTemplate() throws SQLException {
        SingleConnectionDataSource ds = new SingleConnectionDataSource(this.attr.getJdbcConntionString(), this.attr.getUsername(),
                this.attr.getPassword(), false);
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(ds);
        return jdbcTemplate;
    }

    private MapSqlParameterSource getParamSource() {
        MapSqlParameterSource source = new MapSqlParameterSource();
        List<String> replaceKeys = PlaceHolder.getSupportKeys();
        String sql = this.attr.getSqlToRun();
        for (String key : replaceKeys) {
            if (sql.contains(key)) {
                String replaceNewKey = PlaceHolder.replaceKeyWith(key);
                sql = sql.replace(key, replaceNewKey);
                Object value = PlaceHolder.replaceValue(key);
                //:xxxx, have to remove :
                source = source.addValue(replaceNewKey.substring(1), value);
            }
        }
        this.attr.setSqlToRun(sql);
        return source;
    }
    
}
