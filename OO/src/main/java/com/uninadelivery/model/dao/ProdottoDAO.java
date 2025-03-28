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
import com.uninadelivery.model.entities.Prodotto;

public class ProdottoDAO {
    private static final Logger LOGGER = Logger.getLogger(ProdottoDAO.class.getName());

    // Metodo per recuperare tutti i prodotti dal database
    public List<Prodotto> getAllProdotti() {
        List<Prodotto> prodotti = new ArrayList<>();
        String query = "SELECT id_prodotto, nome_prodotto, dimensioni, peso, quantita_disp, prezzo, id_magazzino FROM prodotto";

        try (Connection conn = DBConnection.getDBconnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Prodotto prodotto = new Prodotto(
                        rs.getInt("id_prodotto"),
                        rs.getString("nome_prodotto"),
                        rs.getString("dimensioni"),
                        rs.getDouble("peso"),
                        rs.getInt("quantita_disp"),
                        rs.getDouble("prezzo"),
                        rs.getInt("id_magazzino")
                );
                prodotti.add(prodotto);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore durante il recupero dei prodotti", e);
        }

        return prodotti;
    }

    // Metodo per modificare la quantità di prodotti disponibili
    public void updateQuantita(int prodottoId, int nuovaQuantita) {
        String sql = "UPDATE prodotto SET quantita_disp = ? WHERE id_prodotto = ?";
        try (Connection conn = DBConnection.getDBconnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nuovaQuantita);
            stmt.setInt(2, prodottoId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore durante la modifica della quantità di prodotti", e);
        }
    }

}
