package com.example.patientproject;
import com.example.patientproject.service.Utilities;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

@Component
public class DB {

    private static HikariConfig config;
    private static HikariDataSource ds;
    private static Properties props;

    static {
        try {
            props = new Properties();
            props.load(new Utilities().loadResource("application.properties"));
            config = new HikariConfig(props);
            config.setDriverClassName(org.postgresql.Driver.class.getName());
            ds = new HikariDataSource(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DB() {
        // Prevent instantiation
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static synchronized int executeQuery(String query) throws SQLException {
        int res = 0;
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();
            System.out.println(query);
            res = st.executeUpdate(query);
            st.close();
        }
        return res;
    }

    public static HikariDataSource getDs() {
        return ds;
    }

    public static void setDs(HikariDataSource ds) {
        DB.ds = ds;
    }
}

