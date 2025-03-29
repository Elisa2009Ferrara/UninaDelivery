package com.uninadelivery.controller;

import com.uninadelivery.model.dao.ProgrammazioneDAO;
import com.uninadelivery.model.entities.Programmazione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.SQLException;

public class CreateProgramController {

    @FXML private DatePicker dateProxConsegna;
    @FXML private DatePicker dateDataFine;
    @FXML private ComboBox<String> comboOrario;
    @FXML private ComboBox<String> comboFrequenza;
    @FXML private TextField txtClienteEmail;

    private ProgrammazioneDAO programmazioneDAO = new ProgrammazioneDAO();
    private ObservableList<Programmazione> listaProgrammazioni = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        comboFrequenza.setItems(FXCollections.observableArrayList("settimanale", "mensile", "trimestrale"));

        comboOrario.setItems(FXCollections.observableArrayList("mattina", "pomeriggio"));
    }

    @FXML
    private void salvaProgrammazione(ActionEvent event) {
        if (dateProxConsegna.getValue() == null || dateDataFine.getValue() == null ||
                comboOrario.getValue() == null || comboFrequenza.getValue() == null || txtClienteEmail.getText().isEmpty()) {
            mostraErrore("Compila tutti i campi prima di salvare.");
            return;
        }

        String emailCliente = txtClienteEmail.getText();
        if (!programmazioneDAO.emailClienteEsistente(emailCliente)) {
            mostraErrore("L'email del cliente non esiste nel database.");
            return;
        }

        String frequenza = comboFrequenza.getValue();
        String orario = comboOrario.getValue();

        Programmazione nuovaProg = new Programmazione(
                0,
                dateProxConsegna.getValue(),
                dateDataFine.getValue(),
                orario,
                frequenza,
                emailCliente
        );

        try {
            programmazioneDAO.createProgrammazione(nuovaProg);
            caricaProgrammazioni();
            pulisciCampi();
            mostraInfo("Programmazione salvata con successo.");
        } catch (Exception e) {
            mostraErrore("Errore durante il salvataggio: " + e.getMessage());
        }
    }

    private void caricaProgrammazioni() {
        try {
            listaProgrammazioni.setAll(programmazioneDAO.getAllOrdiniProgrammati());
        } catch (SQLException e) {
            mostraErrore("Errore nel recupero delle programmazioni: " + e.getMessage());
        }
    }

    private void mostraErrore(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void mostraInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successo");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void pulisciCampi() {
        dateProxConsegna.setValue(null);
        dateDataFine.setValue(null);
        comboOrario.getSelectionModel().clearSelection();  // Pulisce la selezione dell'orario
        comboFrequenza.getSelectionModel().clearSelection();
        txtClienteEmail.clear();
    }
}