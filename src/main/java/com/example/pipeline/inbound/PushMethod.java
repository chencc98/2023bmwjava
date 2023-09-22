package com.example.pipeline.inbound;

import com.example.pipeline.entity.FeedInbound;
import com.example.pipeline.service.BatchService;

public interface PushMethod {
    void setupInbound(FeedInbound inbound, PushRequest request);
    void setBatchService(BatchService batchService);
    void startWork();
}
