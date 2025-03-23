package com.uninadelivery.model.dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dbcon = null;
    private Connection conn = null;

    private static final String URL = "jdbc:postgresql://localhost:5432/UninaDelivery";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    private DBConnection() {}

    public static synchronized DBConnection getDBconnection() {
        if (dbcon == null) {
            dbcon = new DBConnection();
        }
        return dbcon;
    }

    public Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName("org.postgresql.Driver");
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connessione al database riuscita!");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Errore durante la connessione al database", e);
        }
        return conn;
    }

    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Connessione chiusa correttamente.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante la chiusura della connessione", e);
        }
    }
}