package com.example.pipeline.inbound;

import com.example.pipeline.entity.FeedInbound;
import com.example.pipeline.service.BatchService;

public interface PullMethod {
    void setupInbound(FeedInbound inbound);
    void setBatchService(BatchService batchService);
    void startWork();
}
