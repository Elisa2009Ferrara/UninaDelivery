package com.uninadelivery.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.uninadelivery.model.dbconnection.DBConnection;
import com.uninadelivery.model.entities.Programmazione;

public class ProgrammazioneDAO {
    private static final Logger LOGGER = Logger.getLogger(ProgrammazioneDAO.class.getName());

    public void createProgrammazione(Programmazione programmazione) {
        String query = "INSERT INTO programmazione (id_programmazione, prox_consegna, data_fine, orario, frequenza)" +
                "VALUES (?, ?, ?, ?, ?) RETURNING id_programmazione";

        try (Connection conn = DBConnection.getDBconnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, programmazione.getIdProgrammazione());
            ps.setDate(2, java.sql.Date.valueOf(programmazione.getProxConsegna()));
            ps.setDate(3, java.sql.Date.valueOf(programmazione.getDataFine()));
            ps.setString(4, programmazione.getOrario());
            ps.setString(5, programmazione.getFrequenza());

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
