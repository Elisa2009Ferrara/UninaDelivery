package com.uninadelivery.model.dbconnection;

import java.sql.Connection;

public class TestDB {
    public static void main(String[] args) {
        try {
            DBConnection db = DBConnection.getDBconnection();
            Connection conn = db.getConnection();
            if (conn != null) {
                System.out.println("Test connessione riuscito!");
                db.closeConnection();
            }
        } catch (RuntimeException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }
}