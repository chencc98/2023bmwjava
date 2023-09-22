package com.example.pipeline;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import com.example.pipeline.inbound.PushRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PushFeedTest {
    public static void main(String[] args) throws IOException {
        String vendor = "TESTV";
        String inboundId = "inbound2";
        String feedName = "testbyapi.csv";
        String pushSecret = "secret-123456";
        String filepath = "C:\\Users\\carltestvm6\\Documents\\testfeed\\aaaaa.txt.txt";
        uploadFeed("TESTV", "inbound2", "testbyapi.csv", "secret-123456", "C:\\Users\\carltestvm6\\Documents\\testfeed\\aaaaa.txt.txt");
        uploadFeed("TESTV", "inbound5", "analysis.csv", "secret-aaa", "C:\\Users\\carltestvm6\\Documents\\testfeed\\analysis.txt");
        //uploadFeed("BGF", "inbound3", "bsf-nav.csv", "secret-bsf", "C:\\Users\\carltestvm6\\Documents\\testfeed\\bsf-nav.csv");
        //uploadFeed("BGF", "inbound4", "bfs-perf.csv", "secret-aaa", "C:\\Users\\carltestvm6\\Documents\\testfeed\\bfs-perf.csv");
    }

    private static void uploadFeed(String vendorCode, String inboundIdi, String feedNa, String secret,
        String filepath) throws IOException {
        String vendor = vendorCode;
        String inboundId = inboundIdi;
        String feedName = feedNa;
        String pushSecret = secret;
        //String filepath = "C:\\Users\\carltestvm6\\Documents\\testfeed\\aaaaa.txt.txt";
        String content = FileUtils.readFileToString(new File(filepath), "UTF-8");
        String base64 = Base64.getEncoder().encodeToString(content.getBytes("UTF-8"));

        PushRequest request = new PushRequest();
        request.setVendorCode(vendor);
        request.setInboundUuid(inboundId);
        request.setFeedName(feedName);
        request.setPushSecret(pushSecret);
        request.setContentBase64(base64);

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(request);

        HttpClient client = HttpClients.createDefault();
        HttpUriRequest req = RequestBuilder.post("https://fund-insight.azurewebsites.net/pushEndpoint/post")
            .setEntity(new StringEntity(json, ContentType.APPLICATION_JSON))
            //.addHeader("Authorization", "Bearer dapi75b229a2f520f1f88f41c7c71f87a444-3")
            .build();
        
        HttpResponse resp = client.execute(req);
        System.out.println(resp.getStatusLine().getStatusCode());
        System.out.println(resp.getEntity().toString());
    }
}
