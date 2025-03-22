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

    /* Metodo usato nella schermata orders */
    public List<Ordine> getAllOrdini() {
        List<Ordine> orders = new ArrayList<>();
        String query = "SELECT o.id_ordine, o.data_ordine, o.completamento, o.email_cliente, " +
                "COALESCE(SUM(d.quantita), 0) AS numero_prodotti " +
                "FROM ordine o " +
                "LEFT JOIN dettaglio_ordine d ON o.id_ordine = d.id_ordine " +
                "GROUP BY o.id_ordine, o.data_ordine, o.completamento, o.email_cliente";

        try (Connection conn = DBConnection.getDBconnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idOrdine = rs.getInt("id_ordine");
                LocalDate dataOrdine = rs.getDate("data_ordine").toLocalDate();
                Boolean completamento = rs.getBoolean("completamento");
                String emailCliente = rs.getString("email_cliente");
                int numeroProdotti = rs.getInt("numero_prodotti");

                Ordine ordine = new Ordine(idOrdine, dataOrdine, completamento, emailCliente, numeroProdotti);
                orders.add(ordine);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore durante il recupero degli ordini", e);
        }

        return orders;
    }




}