package com.uninadelivery.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.uninadelivery.model.dbconnection.DBConnection;
import com.uninadelivery.model.entities.Operatore;


public class OperatoreDAO {
    private static final Logger LOGGER = Logger.getLogger(OperatoreDAO.class.getName());

    private String hashPasswordMD5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.SEVERE, "Errore durante l'hashing della password", e);
            return null;
        }
    }

    public Operatore getOperatoreByEmailPassword(String email, String password) {
        String query = "SELECT * FROM operatore WHERE email_operatore = ?";

        try (Connection conn = DBConnection.getDBconnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, email);
            LOGGER.info("Eseguo query: " + ps.toString());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString("password");

                    String hashedInputPassword = hashPasswordMD5(password);

                    if (hashedPassword.equals(hashedInputPassword)) {
                        LOGGER.info("Login riuscito per l'email: " + email);
                        return new Operatore(
                                rs.getString("email_operatore"),
                                rs.getString("password"),
                                rs.getString("nome_operatore"),
                                rs.getString("cognome_operatore"),
                                rs.getString("n_telefono_operatore")
                        );
                    } else {
                        LOGGER.warning("Password errata per l'email: " + email);
                    }
                } else {
                    LOGGER.warning("Nessun operatore trovato con questa email: " + email);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore durante la verifica delle credenziali dell'operatore", e);
        }
        return null;
    }

    public boolean registerOperatore(Operatore operatore) {
        String query = "INSERT INTO operatore (email_operatore, password, nome_operatore, cognome_operatore, n_telefono_operatore, id_magazzino) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getDBconnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, operatore.getEmailOperatore());
            ps.setString(2, operatore.getPassword());
            ps.setString(3, operatore.getNomeOperatore());
            ps.setString(4, operatore.getCognomeOperatore());
            ps.setString(5, operatore.getnTelefonoOperatore());
            ps.setInt(6, 1);

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore durante la registrazione dell'operatore", e);
            return false;
        }
    }

}