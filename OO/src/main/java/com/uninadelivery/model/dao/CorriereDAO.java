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

    //  Trova il numero di telefono del corriere dato il nome
    public String getNumeroTelefonoByNome(String nomeCorriere) {
        String query = "SELECT n_telefono_corriere FROM Corriere WHERE CONCAT(nome_corriere, ' ', cognome_corriere) = ?";

        try (Connection conn = DBConnection.getDBconnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nomeCorriere);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("n_telefono_corriere");
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore durante la ricerca del numero di telefono del corriere", e);
        }
        return null;
    }

    public List<String> getNomiCorrieriDisponibili() {
        List<String> nomiCorrieri = new ArrayList<>();
        String query = "SELECT CONCAT(nome_corriere, ' ', cognome_corriere) AS nome_completo FROM corriere WHERE disponibilita = true";

        try (Connection conn = DBConnection.getDBconnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                nomiCorrieri.add(rs.getString("nome_completo"));
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore durante il recupero dei nomi dei corrieri disponibili", e);
        }
        return nomiCorrieri;
    }

}