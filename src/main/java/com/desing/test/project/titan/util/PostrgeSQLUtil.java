package com.desing.test.project.titan.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostrgeSQLUtil {

    public Connection getConnection() {
        Connection connection = null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/titanTest");
            connection = ds.getConnection();
            System.out.println("Connection OK");
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
            System.out.println("Connection ERROR");
        }
        return connection;
    }

}
