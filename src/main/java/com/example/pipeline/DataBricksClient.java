package com.example.pipeline;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.databricks.sdk.WorkspaceClient;
import com.databricks.sdk.core.DatabricksConfig;
import com.databricks.sdk.service.files.FileInfo;
import com.databricks.sdk.service.files.Put;

@Component
public class DataBricksClient {
    
    private static String host;
    private static String token;
    private WorkspaceClient client = null;

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

    

    

    public DataBricksClient() {
        DatabricksConfig config = new DatabricksConfig().setHost(this.host)
            .setToken(this.token)
            .setAuthType("pat");
        client = new WorkspaceClient(config);
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
        Put putRequest = new Put();
        putRequest.setOverwrite(true);
        putRequest.setPath(targetFile);
        String content = FileUtils.readFileToString(localFile, "UTF-8");
        content = Base64.getEncoder().encodeToString(content.getBytes("UTF-8"));
        putRequest.setContents(content);
        client.dbfs().put(putRequest);
    }
}
