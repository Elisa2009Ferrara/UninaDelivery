package com.uninadelivery.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.uninadelivery.model.dbconnection.DBConnection;
import com.uninadelivery.model.entities.Corriere;

public class CorriereDAO {
    private static final Logger LOGGER = Logger.getLogger(CorriereDAO.class.getName());

    public void createCorriere(Corriere corriere) {
        String query = "INSERT INTO corriere (n_telefono_corriere, nome_corriere, cognome_corriere, disponibilita) " +
                "VALUES (?, ?, ?, ?) RETURNING n_telefono_corriere";

        try (Connection conn = DBConnection.getDBconnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, corriere.getnTelefonoCorriere());
            ps.setString(2, corriere.getNomeCorriere());
            ps.setString(3, corriere.getCognomeCorriere());
            ps.setBoolean(4, corriere.getDisponibilita());

            ps.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore durante l'inserimento del corriere", e);
        }
    }
}