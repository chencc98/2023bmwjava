package com.example.pipeline.inbound;

import lombok.Data;

@Data
public class PushRequest {
    private String vendorCode;
    private String inboundUuid;
    private String feedName;
    private String pushSecret;
    private String contentBase64;
}
