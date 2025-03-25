package com.uninadelivery.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.uninadelivery.model.dbconnection.DBConnection;
import com.uninadelivery.model.entities.Spedizione;

public class SpedizioneDAO {
    private static final Logger LOGGER = Logger.getLogger(SpedizioneDAO.class.getName());
    private DBConnection dbcon;

    public int createSpedizione(Spedizione spedizione) {
        String query = "INSERT INTO spedizione (destinazione, arrivo_prev, societa, stato, data_sped) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id_spedizione";

        try (Connection conn = DBConnection.getDBconnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, spedizione.getDestinazione());
            ps.setDate(2, java.sql.Date.valueOf(spedizione.getArrivoPrev()));
            ps.setString(3, spedizione.getSocieta());
            ps.setString(4, spedizione.getStato());
            ps.setDate(5, java.sql.Date.valueOf(spedizione.getDataSped()));

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idSpedizione = rs.getInt(1);
                        LOGGER.info("Spedizione inserita con successo, ID: " + idSpedizione);
                        return idSpedizione;
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore durante l'inserimento della spedizione", e);
        }
        return -1;
    }
}