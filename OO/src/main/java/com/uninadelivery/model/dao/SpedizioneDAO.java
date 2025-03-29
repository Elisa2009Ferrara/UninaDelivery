package com.uninadelivery.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.uninadelivery.model.dbconnection.DBConnection;
import com.uninadelivery.model.entities.Spedizione;

public class SpedizioneDAO {
    private static final Logger LOGGER = Logger.getLogger(SpedizioneDAO.class.getName());

    public List<Spedizione> getAllSpedizioni() {
        List<Spedizione> spedizioni = new ArrayList<>();

        String query = "SELECT s.id_spedizione, s.destinazione, s.arrivo_prev, s.societa, s.stato, s.data_sped, " +
                "s.id_ordine, s.n_telefono_corriere, s.targa " +
                "FROM spedizione s";

        try (Connection conn = DBConnection.getDBconnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Spedizione spedizione = new Spedizione(
                        rs.getInt("id_spedizione"),
                        rs.getString("destinazione"),
                        rs.getDate("arrivo_prev").toLocalDate(),
                        rs.getString("societa"),
                        rs.getString("stato"),
                        rs.getDate("data_sped").toLocalDate(),
                        rs.getInt("id_ordine"),
                        rs.getString("n_telefono_corriere"),
                        rs.getString("targa")
                );

                spedizioni.add(spedizione);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore durante il recupero delle spedizioni", e);
        }
        return spedizioni;
    }

    public boolean createSpedizione(Spedizione spedizione) {
        String query = "INSERT INTO spedizione (destinazione, arrivo_prev, societa, stato, data_sped, id_ordine, n_telefono_corriere, targa) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getDBconnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, spedizione.getDestinazione() != null ? spedizione.getDestinazione() : "Non specificata");
            ps.setDate(2, java.sql.Date.valueOf(spedizione.getArrivoPrev()));
            ps.setString(3, spedizione.getSocieta());
            ps.setString(4, spedizione.getStato());
            ps.setDate(5, java.sql.Date.valueOf(spedizione.getDataSped()));
            ps.setInt(6, spedizione.getIdOrdine());
            ps.setString(7, spedizione.getCorriere());  // Numero di telefono del corriere
            ps.setString(8, spedizione.getMezzoTrasporto()); // Targa del mezzo di trasporto

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore nell'inserimento della spedizione", e);
            return false;
        }
    }
}