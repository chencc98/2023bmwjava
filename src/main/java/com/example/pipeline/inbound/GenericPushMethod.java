package com.example.pipeline.inbound;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;

import com.example.pipeline.entity.FeedInbound;
import com.example.pipeline.service.BatchService;
import com.example.pipeline.utils.Constants;

public abstract class GenericPushMethod implements PushMethod {
    protected BatchService batchService;

    public void setBatchService(BatchService batchService) {
        this.batchService = batchService;
    }
    
    protected String saveDataToTemp(List<Map<String, Object>> data) throws IOException {
        //FIX
        return StorageHandler.saveDataToTemp(data);
    }

    protected String saveDataToTemp(String data) throws IOException {
        //FIX
        return StorageHandler.saveDataToTemp(data);
    }

    protected boolean compareWithPrevious(FeedInbound inbound, String current) throws IOException {
        String previous = getPreviousFile(inbound);
        File previousFile = new File(previous);
        if (!previousFile.exists()) {
            return false;
        }
        long checksumPre = FileUtils.checksumCRC32(previousFile);
        long checksumCur = FileUtils.checksumCRC32(new File(current));
        if (checksumCur != checksumPre) {
            return false;
        }
        return true;
    }

    protected String getPreviousFile(FeedInbound inbound) {
        return StorageHandler.getPreviousFile(inbound);
    }

    protected String genBatchId() {
        return UUID.randomUUID().toString();
    }

    protected void copyToWorkArea(FeedInbound inbound, String file, String batchId) throws IOException {
        //copy to dbfs later
        //StorageHandler.copyToWorkArea(inbound, file, batchId);
        StorageHandler.copyToDataBricksWorkArea(inbound, file, batchId);
    }

    protected void createBatchRecord(FeedInbound inbound, String batch) {
        this.batchService.addNewBatch(batch, inbound.getFeedUuid(), inbound.getUuid(), Constants.PULL_EXECUTER_NAME);
    }

    protected void moveToArchive(FeedInbound inbound, String file) throws IOException {
        StorageHandler.moveToArchive(inbound, file);
    }
}
