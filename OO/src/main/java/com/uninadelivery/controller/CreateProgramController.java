package com.uninadelivery.controller;

import com.uninadelivery.model.dao.ProgrammazioneDAO;
import com.uninadelivery.model.entities.Programmazione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.sql.SQLException;

public class CreateProgramController {

    @FXML private DatePicker dateProxConsegna;
    @FXML private DatePicker dateDataFine;
    @FXML private TextField txtOrario;
    @FXML private ComboBox<String> comboFrequenza;
    @FXML private TextField txtClienteEmail;
    @FXML private Button btnSalva;
    @FXML private AnchorPane dragPane;
    private ProgrammazioneDAO programmazioneDAO = new ProgrammazioneDAO();
    private ObservableList<Programmazione> listaProgrammazioni = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        comboFrequenza.setItems(FXCollections.observableArrayList("Settimanale", "Mensile", "Annuale"));
    }

    @FXML
    private void salvaProgrammazione(ActionEvent event) {
        if (dateProxConsegna.getValue() == null || dateDataFine.getValue() == null ||
                txtOrario.getText().isEmpty() || comboFrequenza.getValue() == null || txtClienteEmail.getText().isEmpty()) {
            mostraErrore("Compila tutti i campi prima di salvare.");
            return;
        }

        Programmazione nuovaProg = new Programmazione(
                0,
                dateProxConsegna.getValue(),
                dateDataFine.getValue(),
                txtOrario.getText(),
                comboFrequenza.getValue(),
                txtClienteEmail.getText()
        );

        try {
            programmazioneDAO.createProgrammazione(nuovaProg);
            caricaProgrammazioni(); // Aggiorna la lista delle programmazioni
            pulisciCampi();
        } catch (Exception e) {  // Catch generico per qualsiasi errore
            mostraErrore("Errore durante il salvataggio: " + e.getMessage());
        }
    }

    private void caricaProgrammazioni() {
        // Ricarica la lista delle programmazioni dal database
        try {
            listaProgrammazioni.setAll(programmazioneDAO.getAllOrdiniProgrammati());
        } catch (SQLException e) {
            mostraErrore("Errore nel recupero delle programmazioni: " + e.getMessage());
        }
    }

    private boolean validazioneCampi() {
        return dateProxConsegna.getValue() != null &&
                dateDataFine.getValue() != null &&
                !txtOrario.getText().isEmpty() &&
                comboFrequenza.getValue() != null &&
                !txtClienteEmail.getText().isEmpty();
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
        txtOrario.clear();
        comboFrequenza.getSelectionModel().clearSelection();
        txtClienteEmail.clear();
    }
}