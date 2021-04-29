package com.example.healthanalyzers.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtils {

    private static String jdbc = "com.mysql.jdbc.Driver";
    private static String db = "jdbc:mysql://192.168.220.1:3306/health?useSSL=false&allowPublicKeyRetrieval=true";
    private static String user = "root";
    private static String password = "666666";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(jdbc);
        return DriverManager.getConnection(db, user, password);
    }

    public static void close(Connection connection, Statement statement, ResultSet resultSet) throws SQLException {
        resultSet.close();
        statement.close();
        connection.close();
    }

    public static void close(Connection connection, Statement statement) throws SQLException {
        statement.close();
        connection.close();
    }
}
