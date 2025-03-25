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
import com.uninadelivery.model.entities.MezzoDiTrasporto;

public class MezzoDiTrasportoDAO {
    private static final Logger LOGGER = Logger.getLogger(MezzoDiTrasportoDAO.class.getName());

    public List<String> getMezziDisponibili() {
        List<String> mezzi = new ArrayList<>();
        String query = "SELECT modello FROM mezzo_di_trasporto WHERE disponibilita = true";

        try (Connection conn = DBConnection.getDBconnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                mezzi.add(rs.getString("modello"));
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore durante il recupero dei mezzi di trasporto disponibili", e);
        }
        return mezzi;
    }
}