package com.example.pipeline.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.example.pipeline.entity.FeedInbound;
import com.example.pipeline.inbound.PullMethod;
import com.example.pipeline.service.BatchService;
import com.example.pipeline.service.InboundService;
import com.example.pipeline.utils.Constants;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FeedInboundExecuteTask {
    
    @Autowired
    private InboundService inboundService;

    @Autowired
    private BatchService batchService;

    @Async("inboundAsyncExecutor")
    public void runInboundPull(FeedInbound inbound) {
        Date currentTs = new Date();
        inboundService.updateInboundLastRunTs(inbound, currentTs, Constants.PULL_EXECUTER_NAME);

        log.info("start to get worker");
        PullMethod worker = inbound.getPullWorker();
        if (worker == null) {
            return;
        }
        worker.setupInbound(inbound);
        worker.setBatchService(batchService);
        worker.startWork();
    }
}
