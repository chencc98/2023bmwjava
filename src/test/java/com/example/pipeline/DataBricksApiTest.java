package com.example.pipeline;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import com.databricks.client.jdbc.internal.fasterxml.jackson.databind.ObjectMapper;

public class DataBricksApiTest {
    public static void main(String[] args) throws IOException {
        HttpClient client = HttpClients.createDefault();
        Map<String, String> data = new HashMap<>();
        String cc = FileUtils.readFileToString(new File("D:\\az-training\\temp.txt"), "UTF-8");
        String bb = Base64.getEncoder().encodeToString(cc.getBytes("UTF-8"));
        data.put("path", "dbfs:/FileStore/abc.csv");
        data.put("overwrite", "true");
        data.put("contents", bb);
        com.fasterxml.jackson.databind.ObjectMapper om = new com.fasterxml.jackson.databind.ObjectMapper();
        String json = om.writeValueAsString(data);

        HttpUriRequest request = RequestBuilder.post("https://adb-3828515391694189.9.azuredatabricks.net/api/2.0/dbfs/put")
            .setEntity(new StringEntity(json, ContentType.APPLICATION_JSON))
            .addHeader("Authorization", "Bearer dapi75b229a2f520f1f88f41c7c71f87a444-3")
            .build();
        
        HttpResponse resp = client.execute(request);
        System.out.println(resp.getStatusLine().getStatusCode());
        System.out.println(resp.getEntity().toString());

        
    }
}
