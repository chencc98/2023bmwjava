package com.example.pipeline.inbound;

import java.util.Base64;

import com.example.pipeline.entity.FeedInbound;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PushByApiWorker extends GenericPushMethod {
    private FeedInbound inbound;
    private PushRequest request;
    @Override
    public void setupInbound(FeedInbound inbound, PushRequest request) {
        this.inbound = inbound;
        this.request = request;
    }

    @Override
    public void startWork() {
        String base64 = request.getContentBase64();
        byte[] bytes = Base64.getDecoder().decode(base64);
        try {
            String content = new String(bytes, "UTF-8");
            String filepath = saveDataToTemp(content);
            log.info("save data to temp file:" + filepath);
            boolean isSame = compareWithPrevious(this.inbound, filepath);
            if (isSame) {
                log.info("the same content of data. skip...");
                //remove temp?
                return;
            }
            String batchId = genBatchId();
            log.info("try to copy to workarea ...");
            copyToWorkArea(this.inbound, filepath, batchId);
            
            createBatchRecord(this.inbound, batchId);
            moveToArchive(inbound, filepath);
            log.info("work is done");
        } catch (Exception e) {
            log.error(e.getMessage() , e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
}
