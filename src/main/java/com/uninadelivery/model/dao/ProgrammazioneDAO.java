package com.uninadelivery.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.uninadelivery.model.dbconnection.DBConnection;
import com.uninadelivery.model.entities.Programmazione;

public class ProgrammazioneDAO {
    private static final Logger LOGGER = Logger.getLogger(ProgrammazioneDAO.class.getName());

    public void createProgrammazione(Programmazione programmazione) {
        String query = "INSERT INTO programmazione (id_programmazione, prox_consegna, data_fine, orario, frequenza)" +
                "VALUES (?, ?, ?, ?, ?) RETURNING id_programmazione";

        try (Connection conn = DBConnection.getDBconnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, programmazione.getIdProgrammazione());
            ps.setDate(2, java.sql.Date.valueOf(programmazione.getProxConsegna()));
            ps.setDate(3, java.sql.Date.valueOf(programmazione.getDataFine()));
            ps.setString(4, programmazione.getOrario());
            ps.setString(5, programmazione.getFrequenza());

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

    // Metodo per ottenere tutti gli ordini programmati
    public List<Programmazione> getAllOrdiniProgrammati() throws SQLException {
        String query = "SELECT * FROM programmazione";  // Assicurati che questa query sia corretta per il tuo caso
        List<Programmazione> ordini = new ArrayList<>();

        try (Connection conn = DBConnection.getDBconnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idProgrammazione = rs.getInt("id_programmazione");
                LocalDate proxConsegna = rs.getDate("prox_consegna").toLocalDate();
                LocalDate dataFine = rs.getDate("data_fine").toLocalDate();
                String orario = rs.getString("orario");
                String frequenza = rs.getString("frequenza");
                String clienteEmail = rs.getString("cliente_email");

                Programmazione programmazione = new Programmazione(idProgrammazione, proxConsegna, dataFine, orario, frequenza, clienteEmail);
                ordini.add(programmazione);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore nel recupero degli ordini programmati", e);
            throw new SQLException("Errore nel recupero degli ordini programmati", e);
        }

        return ordini;
    }

    // Metodo per aggiornare un ordine programmato
    public void aggiornaOrdineProgrammato(Programmazione ordine) throws SQLException {
        String query = "UPDATE programmazione SET prox_consegna = ?, data_fine = ?, orario = ?, frequenza = ? WHERE id_programmazione = ?";

        try (Connection conn = DBConnection.getDBconnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setDate(1, java.sql.Date.valueOf(ordine.getProxConsegna()));
            ps.setDate(2, java.sql.Date.valueOf(ordine.getDataFine()));
            ps.setString(3, ordine.getOrario());
            ps.setString(4, ordine.getFrequenza());
            ps.setInt(5, ordine.getIdProgrammazione());

            ps.executeUpdate();
            LOGGER.info("Ordine programmato aggiornato con successo: " + ordine.getIdProgrammazione());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore durante l'aggiornamento dell'ordine programmato", e);
            throw new SQLException("Errore durante l'aggiornamento dell'ordine programmato", e);
        }
    }

    // Metodo per eliminare un ordine programmato
    public void eliminaOrdineProgrammato(int idProgrammazione) throws SQLException {
        String query = "DELETE FROM programmazione WHERE id_programmazione = ?";

        try (Connection conn = DBConnection.getDBconnection().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, idProgrammazione);
            ps.executeUpdate();
            LOGGER.info("Ordine programmato eliminato con successo: " + idProgrammazione);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Errore durante l'eliminazione dell'ordine programmato", e);
            throw new SQLException("Errore durante l'eliminazione dell'ordine programmato", e);
        }
    }
}