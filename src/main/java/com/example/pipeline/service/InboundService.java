package com.example.pipeline.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pipeline.dao.FeedDao;
import com.example.pipeline.entity.FeedInbound;

@Service
public class InboundService {
    
    @Autowired
    private FeedDao feedDao;

    public List<FeedInbound> getAllPullInbound() {
        return feedDao.getAllPullInbounds();
    }

    public void updateInboundLastRunTs(FeedInbound inbound, Date lastRunTs, String who) {
        feedDao.updateInboundLastRunTs(inbound, who, lastRunTs);
    }
}
