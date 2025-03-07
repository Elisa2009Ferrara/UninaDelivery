package com.uninadelivery.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.uninadelivery.model.dbconnection.DBConnection;
import com.uninadelivery.model.entities.Operatore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class OperatoreDAO {
    private static final Logger LOGGER = Logger.getLogger(OperatoreDAO.class.getName());

    // Metodo per ottenere l'hash MD5 della password, siccome la password è hashata nel database
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

                    // Hash della password immessa dall'utente usando MD5
                    String hashedInputPassword = hashPasswordMD5(password);

                    if (hashedPassword.equals(hashedInputPassword)) { // Confronta la password hashata
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
}