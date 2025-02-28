package com.uninadelivery.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.uninadelivery.model.dbconnection.DBConnection;
import com.uninadelivery.model.entities.Operatore;

public class OperatoreDAO {
    private static final Logger LOGGER = Logger.getLogger(OperatoreDAO.class.getName());

    public void createOperatore(Operatore operatore) {
        String query = "INSERT INTO operatore (email_operatore, password, nome_operatore, cognome_operatore, n_telefono_operatore) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING email_operatore";

        try (Connection conn = DBConnection.getDBconnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, operatore.getEmailOperatore());
            ps.setString(2, operatore.getPassword());
            ps.setString(3, operatore.getNomeOperatore());
            ps.setString(4, operatore.getCognomeOperatore());
            ps.setString(5, operatore.getnTelefonoOperatore());

            ps.executeUpdate();
            LOGGER.info("Operatore inserito con successo: " + operatore.getEmailOperatore());

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore durante l'inserimento dell'operatore", e);
        }
    }

    public Operatore getOperatoreByEmail(String email) {
        String query = "SELECT * FROM operatore WHERE email_operatore = ?";
        Operatore operatore = null;

        try (Connection conn = DBConnection.getDBconnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    operatore = new Operatore(
                            rs.getString("email_operatore"),
                            rs.getString("password"),
                            rs.getString("nome_operatore"),
                            rs.getString("cognome_operatore"),
                            rs.getString("n_telefono_operatore")
                    );
                }
            }
            LOGGER.info("Operatore trovato con email: " + email);

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore durante la ricerca dell'operatore", e);
        }
        return operatore;
    }

    public Operatore getOperatoreByEmailPassword(String email, String password) {
        String query = "SELECT * FROM operatore WHERE email_operatore = ? AND password = ?";
        Operatore operatore = null;

        try (Connection conn = DBConnection.getDBconnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    operatore = new Operatore(
                            rs.getString("email_operatore"),
                            rs.getString("password"),
                            rs.getString("nome_operatore"),
                            rs.getString("cognome_operatore"),
                            rs.getString("n_telefono_operatore")
                    );
                }
            }
            LOGGER.info("Login operatore con email: " + email);

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore durante la verifica delle credenziali dell'operatore", e);
        }
        return operatore;
    }
}