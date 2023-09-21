package com.example.pipeline.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.pipeline.entity.BatchStatus;
//import com.example.pipeline.entity.InboundType;

@Component
public class BatchDao {
    private String newBatchSql = "insert into batch (uuid, feed_uuid, inbound_uuid,status,message,"
        + "created_by, last_updated_by) values ("
        + ":uuid, :feedUuid, :inboundUuid, :status, :message, :cuser, :uuser)";

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public void addNewBatch(String batchId, String feedId, String inboundId, String bywho) {
        MapSqlParameterSource source = new MapSqlParameterSource()
            .addValue("uuid", batchId)
            .addValue("feedUuid", feedId)
            .addValue("inboundUuid", inboundId)
            .addValue("status", BatchStatus.RECEIVED.name())
            .addValue("message", "")
            .addValue("cuser", bywho)
            .addValue("uuser", bywho);
        jdbcTemplate.update(newBatchSql, source);
    }
}
