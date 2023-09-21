package com.example.pipeline.inbound;

import lombok.Data;

@Data
public class PullDatabaseAttr {
    private String jdbcConntionString;
    private String username;
    private String password;
    private String sqlToRun;
    
}
