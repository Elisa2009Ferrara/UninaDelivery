package com.uninadelivery.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.uninadelivery.model.dbconnection.DBConnection;
import com.uninadelivery.model.entities.MezzoDiTrasporto;

public class MezzoDiTrasportoDAO {
    private static final Logger LOGGER = Logger.getLogger(MezzoDiTrasportoDAO.class.getName());

    public void createMezzoDiTrasporto(MezzoDiTrasporto mezzoDiTrasporto) {
        String query = "INSERT INTO mezzo_di_trasporto (targa, capacita_peso, capacita_spazio, disponibilita, modello) " +
                "VALUES (?, ?, ?, ?) RETURNING targa";

        try (Connection conn = DBConnection.getDBconnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, mezzoDiTrasporto.getTarga());
            ps.setInt(2, mezzoDiTrasporto.getCapacitaPeso());
            ps.setInt(3, mezzoDiTrasporto.getCapacitaSpazio());
            ps.setBoolean(4, mezzoDiTrasporto.getDisponibilita());
            ps.setString(5, mezzoDiTrasporto.getModello());

            ps.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore durante l'inserimento del mezzo di trasporto", e);
        }
    }
}