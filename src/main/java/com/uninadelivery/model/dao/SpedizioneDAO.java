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

    public void createSpedizione(Spedizione spedizione) {
        String query = "INSERT INTO spedizione (id_spedizione, destinazione, arrivo_prev, societa, stato, data_sped) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING id_spedizione";

        try (Connection conn = DBConnection.getDBconnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, spedizione.getIdSpedizione());
            ps.setString(2, spedizione.getDestinazione());
            ps.setDate(3, java.sql.Date.valueOf(spedizione.getArrivoPrev()));
            ps.setString(4, spedizione.getSocieta());
            ps.setString(5, spedizione.getStato());
            ps.setDate(6, java.sql.Date.valueOf(spedizione.getDataSped()));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idGenerato = rs.getInt(1);
                    LOGGER.info("Prodotto inserito con successo, ID generato: " + idGenerato);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore durante l'inserimento del prodotto", e);
        }
    }
}