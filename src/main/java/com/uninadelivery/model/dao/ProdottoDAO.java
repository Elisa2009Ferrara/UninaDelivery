package com.uninadelivery.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.uninadelivery.model.dbconnection.DBConnection;
import com.uninadelivery.model.entities.Prodotto;

public class ProdottoDAO {
    private static final Logger LOGGER = Logger.getLogger(ProdottoDAO.class.getName());

    public void createProdotto(Prodotto prodotto) {
        String query = "INSERT INTO prodotto (id_prodotto, nome_prodotto, dimensioni, peso, quantita_disp, prezzo) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING id_prodotto";

        try (Connection conn = DBConnection.getDBconnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, prodotto.getIdProdotto());
            ps.setString(2, prodotto.getNomeProdotto());
            ps.setString(3, prodotto.getDimensioni());
            ps.setDouble(4, prodotto.getPeso());
            ps.setInt(5, prodotto.getQuantitaDisp());
            ps.setDouble(6, prodotto.getPrezzo());

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