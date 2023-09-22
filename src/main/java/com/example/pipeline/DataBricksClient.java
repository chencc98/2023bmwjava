package com.example.pipeline;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.databricks.sdk.WorkspaceClient;
import com.databricks.sdk.core.DatabricksConfig;
import com.databricks.sdk.service.files.FileInfo;
import com.databricks.sdk.service.files.Put;
import com.example.pipeline.entity.BatchStatus;
import com.example.pipeline.utils.Constants;
import com.example.pipeline.utils.MyUtils;

@Component
public class DataBricksClient {
    
    private static String host;
    private static String token;
    private static String jdbcConnString;
    private BmwWsClient client = null;

    //private static DataBricksClient self = null;

    private static String dbfsRoot = "dbfs:/FileStore";

    @Value("${databricks.host}")
    public void setHost(String host) {
        this.host = host;
    }


    @Value("${databricks.PWD}")
    public void setToken(String token) {
        this.token = token;
    }

    
    @Value("${databricks.url}")
    public void setJdbcUrl(String url) {
        this.jdbcConnString = url;
    }
    

    public DataBricksClient() {
        DatabricksConfig config = new DatabricksConfig().setHost(this.host)
            .setToken(this.token)
            .setAuthType("pat");
        client = new BmwWsClient(config);
    }

    public void createFolder(String folder) {
        if (!folder.startsWith("/")) {
            folder = "/" + folder;
        }
        String targetFolder = dbfsRoot + folder;
        try {
            FileInfo info = client.dbfs().getStatus(targetFolder);
            info.getIsDir();
        } catch (Exception e) {
            //throws error, not exist
            client.dbfs().mkdirs(targetFolder);
        }

    }

    public void uploadFile(String folder, String newFilename, File localFile) throws IOException {
        if (!folder.startsWith("/")) {
            folder = "/" + folder;
        }
        String targetFolder = dbfsRoot + folder;
        if (!newFilename.startsWith("/")) {
            newFilename = "/" + newFilename;
        }
        String targetFile = targetFolder + newFilename;
        uploadByRestApi(localFile, targetFile, true);
    }

    private void uploadByRestApi(File locaFile, String dbFullPath, boolean overwrite) throws IOException {
        Map<String, String> data = new HashMap<>();
        String cc = FileUtils.readFileToString(locaFile, "UTF-8");
        String bb = Base64.getEncoder().encodeToString(cc.getBytes("UTF-8"));
        data.put("path", dbFullPath);
        data.put("overwrite", String.valueOf(overwrite));
        data.put("contents", bb);
        //com.fasterxml.jackson.databind.ObjectMapper om = new com.fasterxml.jackson.databind.ObjectMapper();
        String json = MyUtils.obj2Str(data);

        HttpUriRequest request = RequestBuilder.post(this.host + "/api/2.0/dbfs/put")
            .setEntity(new StringEntity(json, ContentType.APPLICATION_JSON))
            .addHeader("Authorization", "Bearer " + this.token)
            .build();
        
        HttpClient client = HttpClients.createDefault();
        HttpResponse resp = client.execute(request);

        if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new RuntimeException("" + resp.getStatusLine().getStatusCode()
                + " " + resp.getStatusLine().getReasonPhrase()
                + " " + resp.getEntity().toString());
        }
    }

    public void insertDatabricksBatch(String batchId, String feedId, String inboundId,
        String path)
    {
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        String myroot = dbfsRoot.substring(5);
        path = myroot + path;
        Properties p = new Properties();
        p.put("PWD", this.token);
        String sql = "insert into dbs_fund_insight.batch values(?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(this.jdbcConnString, p)) {
            if (conn != null) {
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, batchId);
                pst.setString(2, feedId);
                pst.setString(3, inboundId);
                pst.setInt(4, -1);
                pst.setString(5, path);
                pst.setString(6, BatchStatus.NEW.name());
                pst.setString(7, "");
                pst.setString(8, Constants.PULL_EXECUTER_NAME);
                pst.setTimestamp(9, Timestamp.from(Instant.now()));
                pst.setString(10, Constants.PULL_EXECUTER_NAME);
                pst.setTimestamp(11, Timestamp.from(Instant.now()));
                pst.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
