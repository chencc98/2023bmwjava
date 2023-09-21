package com.example.pipeline;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SparkConnectTest {
    public static void main(String[] args) {
        String url = "jdbc:databricks://adb-3828515391694189.9.azuredatabricks.net:443/default;transportMode=http;ssl=1;AuthMech=3;httpPath=/sql/1.0/warehouses/5f1b80c0bf87c0ea;"
            + "UID=chencc98@163.com;PWD=dapi75b229a2f520f1f88f41c7c71f87a444-3";
        Properties properties = new Properties();
        //properties.put("PWD", "dapi75b229a2f520f1f88f41c7c71f87a444-3");
        //properties.put("UID", "chencc98@163.com");
        try (Connection connection = DriverManager.getConnection(url, properties)) {
            if (connection != null) {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SHOW SCHEMAS");
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("databaseName"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }   
}
