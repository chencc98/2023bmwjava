package com.example.pipeline.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.pipeline.entity.FeedInbound;
import com.example.pipeline.service.InboundService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FeedInboundScheduleTask {
    @Autowired
    private InboundService inboundService;

    @Autowired
    private FeedInboundExecuteTask executeTask;

    @Scheduled(cron ="0 * * * * *")
    public void startPullSchedule() {
        log.info("startPullSchedule");
        List<FeedInbound> list = inboundService.getAllPullInbound();
        log.info("total pull records: " + list.size());

        List<FeedInbound> needToRun = new ArrayList<>();
        long currentMs = System.currentTimeMillis();
        for (FeedInbound fid : list) {
            if (fid.needToRun(currentMs)) {
                needToRun.add(fid);
            }
        }
        log.info("need to run size: " + needToRun.size());

        for (FeedInbound fid : needToRun) {
            executeTask.runInboundPull(fid);
        }
        log.info("this schedule run is done");
    }
}
