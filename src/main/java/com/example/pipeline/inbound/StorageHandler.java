package com.example.pipeline.inbound;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.example.pipeline.AppProp;
import com.example.pipeline.DataBricksClient;
import com.example.pipeline.entity.FeedInbound;
import com.example.pipeline.utils.MyUtils;

public class StorageHandler {
    private static String STORAGE_ROOT = AppProp.getStorageRoot();
    public static String saveDataToTemp(List<Map<String, Object>> data) throws IOException {
        //FIX
        File temp = File.createTempFile("saveDataToTemp", ".csv");
        Map<String, Object> first = data.get(0);
        String[] headers = first.keySet().toArray(new String[0]);
        String split = ",";
        PrintWriter pw = new PrintWriter(temp, "UTF-8");
        for (int i = 0; i < headers.length; i++) {
            String hd = headers[i];
            pw.print(hd);
            if (i < headers.length - 1) {
                pw.print(split);
            }
        }
        pw.println();
        for (Map<String, Object> item : data) {
            for (int i = 0; i < headers.length; i++) {
                String hd = headers[i];
                Object value = item.get(hd);
                if (value != null) {
                    pw.print(value.toString());
                }
                if (i < headers.length - 1) {
                    pw.print(split);
                }
            }
            pw.println();
        }
        pw.close();
        return temp.getAbsolutePath();
    }

    public static String getPreviousFile(FeedInbound inbound) {
        String folder = STORAGE_ROOT + AppProp.getLastRunFolder();
        folder += inbound.getVendorCode().toLowerCase() + "/";
        String file = folder + inbound.getFeedName();
        File temp = new File(file);
        return temp.getAbsolutePath();
    }

    public static void copyToWorkArea(FeedInbound inbound, String file, String batchId) throws IOException {
        String folder = STORAGE_ROOT + AppProp.getWorkareaFolder();
        folder += inbound.getVendorCode().toLowerCase();
        File thisFolder = new File(folder);
        if (!thisFolder.exists()) {
            thisFolder.mkdirs();
        }
        // String feedname = inbound.getFeedName();
        // String prefix = MyUtils.getFilePrefix(feedname);
        // String suffix = MyUtils.getFileSuffix(feedname);
        // String ts = MyUtils.dateToFullString(new Date());
        // String newFilename = prefix + "_" + ts + "_" + batchId + "." + suffix;
        String newFilename = genWorkareaFile(inbound, batchId);
        FileUtils.copyFile(new File(file), new File(folder + "/" + newFilename));
    }

    private static String genWorkareaFile(FeedInbound inbound, String batchId) {
        String feedname = inbound.getFeedName();
        String prefix = MyUtils.getFilePrefix(feedname);
        String suffix = MyUtils.getFileSuffix(feedname);
        String ts = MyUtils.dateToFullString(new Date());
        String newFilename = prefix + "_" + ts + "_" + batchId + "." + suffix;
        return newFilename;
    }

    public static void copyToDataBricksWorkArea(FeedInbound inbound, String file, String batchId) throws IOException {
        //upload the file first
        DataBricksClient dbClient = new DataBricksClient();
        dbClient.createFolder(AppProp.getWorkareaOnDataBricks());
        String newFilename = genWorkareaFile(inbound, batchId);
        //FileUtils.copyFile(new File(file), new File(folder + "/" + newFilename));
        dbClient.uploadFile(AppProp.getWorkareaOnDataBricks(), newFilename, new File(file));

        //insert a sql
    }

    public static void moveToArchive(FeedInbound inbound, String file) throws IOException {
        String folder = STORAGE_ROOT + AppProp.getLastRunFolder();
        folder += inbound.getVendorCode().toLowerCase();
        File thisFolder = new File(folder);
        if (!thisFolder.exists()) {
            thisFolder.mkdirs();
        }
        File target = new File(folder + "/" + inbound.getFeedName());
        FileUtils.deleteQuietly(target);
        FileUtils.moveFile(new File(file), target, StandardCopyOption.REPLACE_EXISTING);
    }
}
