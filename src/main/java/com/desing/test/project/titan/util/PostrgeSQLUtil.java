package com.desing.test.project.titan.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostrgeSQLUtil {

    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/titandb";
    private static final String DB_USERNAME = "titan_user";
    private static final String DB_PASSWORD = "123456";

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("Connection OK");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Connection ERROR");
        }
        return connection;
    }

}
