package com.example.pipeline;

import com.databricks.sdk.WorkspaceClient;
import com.databricks.sdk.core.DatabricksConfig;
import com.databricks.sdk.service.files.FileInfo;
import com.databricks.sdk.service.sql.EndpointInfo;
import com.databricks.sdk.service.sql.ListWarehousesRequest;

public class DatabricksSdkTest {
    public static void main(String[]  args) {
        DatabricksConfig config = new DatabricksConfig().setHost("https://adb-3828515391694189.9.azuredatabricks.net")
            .setToken("dapi75b229a2f520f1f88f41c7c71f87a444-3");
        WorkspaceClient client = new WorkspaceClient(config);

        for (EndpointInfo endpoint : client.warehouses().list(new ListWarehousesRequest())) {
            System.out.println(endpoint.getName());
            System.out.println(endpoint.getId());
        }

        FileInfo info = client.dbfs().getStatus("/abc-folder");
        System.out.println(info);
    }
}
