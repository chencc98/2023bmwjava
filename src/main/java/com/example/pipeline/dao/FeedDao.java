package com.example.pipeline.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.pipeline.entity.Feed;
import com.example.pipeline.entity.FeedInbound;
import com.example.pipeline.entity.InboundType;

@Component
public class FeedDao {
    private static String getAllFeedSql = "select fd.uuid,vendor_uuid, v.name as vendor_name, fd.name "
        + "from vendor v, feed fd "
        + "where fd.vendor_uuid = v.uuid";
    private static String getAllFeedInboundSql = "select fid.uuid,fid.feed_uuid,inbound_type,"
        + "pull.code as pull_method_code, pull.worker_class as pull_method_worker,"
        + "st.code as schedule_code, fid.schedule_value,fid.pull_attr,"
        + "push.code as push_method_code, push.worker_class as push_method_worker,"
        + "fid.push_attr, fid.previous_run_ts, fd.name as feed_name,v.code as vendor_code "
        + "from feed_inbound fid join feed fd on fid.feed_uuid = fd.uuid "
        + " join vendor v on fd.vendor_uuid = v.uuid "
        + "left join pull_method pull on fid.pull_method_uuid = pull.uuid "
        + " left join schedule_type st on fid.schedule_type_uuid = st.uuid "
        + " left join push_method push on fid.push_method_uuid = push.uuid "
        + "where inbound_type = :inbound_type";

    private static String updateInboundLastRunTsSql = "update feed_inbound set previous_run_ts ="
        + " :last_run_ts, last_updated_by = :updated_by, last_updated_ts = :updated_ts where uuid = :uuid";
    
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;


    public List<Feed> getAllFeeds() {
        List<Feed> list = jdbcTemplate.query(getAllFeedSql, new BeanPropertyRowMapper<Feed>(Feed.class));
        return list;
    }

    public List<FeedInbound> getAllPullInbounds() {
        MapSqlParameterSource source = new MapSqlParameterSource()
            .addValue("inbound_type", InboundType.PULL.name());
        List<FeedInbound> list = jdbcTemplate.query(getAllFeedInboundSql, source,
            new BeanPropertyRowMapper<FeedInbound>(FeedInbound.class));
        return list;
    }

    public void updateInboundLastRunTs(FeedInbound inbound, String bywho, Date lastRunTs) {
        MapSqlParameterSource source = new MapSqlParameterSource()
            .addValue("last_run_ts", lastRunTs)
            .addValue("updated_by", bywho)
            .addValue("updated_ts", new Date())
            .addValue("uuid", inbound.getUuid());
        jdbcTemplate.update(updateInboundLastRunTsSql, source);
    }

}
