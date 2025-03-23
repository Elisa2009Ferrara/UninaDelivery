package com.uninadelivery.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.uninadelivery.model.dbconnection.DBConnection;
import com.uninadelivery.model.entities.DettaglioOrdine;

public class DettaglioOrdineDAO {
    private static final Logger LOGGER = Logger.getLogger(DettaglioOrdineDAO.class.getName());
    private DBConnection dbcon;

    public void createDettaglioOrdine(DettaglioOrdine dettaglioOrdine) throws SQLException {
        dbcon = DBConnection.getDBconnection();
        String query = "INSERT INTO dettaglio_ordine (id_dettaglio, quantita) VALUES (?, ?) RETURNING id_dettaglio";

        try (Connection conn = dbcon.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, dettaglioOrdine.getIdDettaglio());
            ps.setInt(2, dettaglioOrdine.getQuantita());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                LOGGER.log(Level.INFO, "DettaglioOrdine inserito con successo: {0}", dettaglioOrdine);
            } else {
                LOGGER.log(Level.WARNING, "Nessun dettaglio ordine inserito.");
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore durante l'inserimento di DettaglioOrdine", e);
            throw e;  // Rilancia l'eccezione per la gestione a livello superiore
        }
    }
}