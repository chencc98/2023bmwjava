package com.example.pipeline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pipeline.dao.BatchDao;

@Service
public class BatchService {
    @Autowired
    private BatchDao batchDao;
    public void addNewBatch(String batchId, String feedId, String inboundId, String bywho) {
        batchDao.addNewBatch(batchId, feedId, inboundId, bywho);
    }
}
