package com.uninadelivery.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.uninadelivery.model.dbconnection.DBConnection;
import com.uninadelivery.model.entities.Ordine;

public class OrdineDAO {
    private static final Logger LOGGER = Logger.getLogger(OrdineDAO.class.getName());

    public void createOrdine(Ordine ordine) {
        String query = "INSERT INTO ordine (id_ordine, data_ordine, completamento) VALUES (?, ?, ?) RETURNING id_ordine";

        try (Connection conn = DBConnection.getDBconnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, ordine.getIdOrdine());
            ps.setDate(2, java.sql.Date.valueOf(ordine.getDataOrdine()));
            ps.setBoolean(3, ordine.getCompletamento());

            ps.executeUpdate();
            LOGGER.info("Ordine inserito con successo: " + ordine.getIdOrdine());

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore durante l'inserimento dell'ordine", e);
        }
    }

    public List<Ordine> getAllOrdini() {
        List<Ordine> orders = new ArrayList<>();
        String query = "SELECT id_ordine, data_ordine, completamento, email_cliente FROM ordine";

        try (Connection conn = DBConnection.getDBconnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idOrdine = rs.getInt("id_ordine");
                LocalDate dataOrdine = rs.getDate("data_ordine").toLocalDate();
                Boolean completamento = rs.getBoolean("completamento");
                String emailCliente = rs.getString("email_cliente");

                Ordine ordine = new Ordine(idOrdine, dataOrdine, completamento, emailCliente);
                orders.add(ordine);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore durante il recupero degli ordini", e);
        }

        return orders;
    }
}