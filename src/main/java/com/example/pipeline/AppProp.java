package com.example.pipeline;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

//import lombok.Value;

@Configuration
public class AppProp {
    
    private  static String storageRoot;

    public static String getStorageRoot() {
        return storageRoot;
    }

    @Value("${app.storage.root}")
    public void setStorageRoot(String root) {
        storageRoot = root;
    }

    private  static String lastRunFolder;

    public static String getLastRunFolder() {
        return lastRunFolder;
    }

    @Value("${app.storage.lastrun}")
    public void setLastRunFolder(String folder) {
        lastRunFolder = folder;
    }

    private  static String workareaFolder;

    public static String getWorkareaFolder() {
        return workareaFolder;
    }

    @Value("${app.storage.workarea}")
    public void setWorkareaFolder(String folder) {
        workareaFolder = folder;
    }

    private  static String workareaOnDataBricks;

    public static String getWorkareaOnDataBricks() {
        return workareaOnDataBricks;
    }

    @Value("${databricks.dbfs.workarea}")
    public void setWorkareaOnDataBricks(String folder) {
        workareaOnDataBricks = folder;
    }
}
