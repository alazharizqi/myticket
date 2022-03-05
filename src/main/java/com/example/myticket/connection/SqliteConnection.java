package com.example.myticket.connection;
import java.sql.*;

public class SqliteConnection {

    public static Connection Connector() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:myticket.db");
            return connection;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

}
