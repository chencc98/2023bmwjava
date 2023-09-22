package com.example.pipeline.controller;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pipeline.entity.FeedInbound;
import com.example.pipeline.inbound.CodeMessageVo;
import com.example.pipeline.inbound.PushMethod;
import com.example.pipeline.inbound.PushRequest;
import com.example.pipeline.service.BatchService;
import com.example.pipeline.service.InboundService;

@RestController
@RequestMapping("/pushEndpoint")
public class PushMethodController {
    
    @Autowired
    private InboundService inboundService;
    @Autowired
    private BatchService batchService;

    @PostMapping("/post")
    public CodeMessageVo pushFeed(@RequestBody PushRequest request) {
        FeedInbound inbound = inboundService.getInboundByPushRequest(request);
        if (inbound == null) {
            CodeMessageVo vo = new CodeMessageVo();
            vo.setCode(HttpStatus.SC_NOT_FOUND);
            vo.setMessage("parameter errors. not found.");
            return vo;
        }
        try {
            PushMethod method = inbound.getPushWorker();
            method.setupInbound(inbound, request);
            method.setBatchService(batchService);
            method.startWork();
            CodeMessageVo vo = new CodeMessageVo();
            vo.setCode(HttpStatus.SC_OK);
            vo.setMessage("start the processing");
            return vo;
        } catch (Exception e) {
            CodeMessageVo vo = new CodeMessageVo();
            vo.setCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            vo.setMessage("Error: " + e.getMessage());
            return vo;
        }
    }
}
