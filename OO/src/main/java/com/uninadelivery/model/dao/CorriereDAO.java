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
import com.uninadelivery.model.entities.Corriere;

public class CorriereDAO {
    private static final Logger LOGGER = Logger.getLogger(CorriereDAO.class.getName());

    public List<String> getCorrieriDisponibili() {
        List<String> corrieri = new ArrayList<>();
        String query = "SELECT nome_corriere FROM corriere WHERE disponibilita = true";

        try (Connection conn = DBConnection.getDBconnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                corrieri.add(rs.getString("nome_corriere"));
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore durante il recupero dei corrieri disponibili", e);
        }
        return corrieri;
    }
}