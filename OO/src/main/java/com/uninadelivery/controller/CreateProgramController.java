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
    @FXML private ComboBox<String> comboOrario;  // ComboBox per selezionare l'orario
    @FXML private ComboBox<String> comboFrequenza;
    @FXML private TextField txtClienteEmail;
    @FXML private Button btnSalva;
    @FXML private AnchorPane dragPane;

    private ProgrammazioneDAO programmazioneDAO = new ProgrammazioneDAO();
    private ObservableList<Programmazione> listaProgrammazioni = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Imposta la ComboBox della frequenza con i valori ammessi dal vincolo
        comboFrequenza.setItems(FXCollections.observableArrayList("settimanale", "mensile", "trimestrale"));

        // Imposta la ComboBox dell'orario con i valori ammessi dal vincolo
        comboOrario.setItems(FXCollections.observableArrayList("mattina", "pomeriggio"));
    }

    @FXML
    private void salvaProgrammazione(ActionEvent event) {
        // Validazione che tutti i campi siano compilati
        if (dateProxConsegna.getValue() == null || dateDataFine.getValue() == null ||
                comboOrario.getValue() == null || comboFrequenza.getValue() == null || txtClienteEmail.getText().isEmpty()) {
            mostraErrore("Compila tutti i campi prima di salvare.");
            return;
        }

        // Verifica se l'email esiste nel database
        String emailCliente = txtClienteEmail.getText();
        if (!programmazioneDAO.emailClienteEsistente(emailCliente)) {
            mostraErrore("L'email del cliente non esiste nel database.");
            return;
        }

        // I valori sono già in minuscolo, in base alla ComboBox
        String frequenza = comboFrequenza.getValue();
        String orario = comboOrario.getValue();

        // Crea una nuova programmazione
        Programmazione nuovaProg = new Programmazione(
                0,
                dateProxConsegna.getValue(),
                dateDataFine.getValue(),
                orario,  // Usa il valore della ComboBox dell'orario
                frequenza,
                emailCliente
        );

        try {
            programmazioneDAO.createProgrammazione(nuovaProg);  // Salva la programmazione nel database
            caricaProgrammazioni();  // Ricarica la lista delle programmazioni
            pulisciCampi();  // Pulisce i campi
            mostraInfo("Programmazione salvata con successo.");
        } catch (Exception e) {  // Gestione degli errori
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
        // Pulisce i campi
        dateProxConsegna.setValue(null);
        dateDataFine.setValue(null);
        comboOrario.getSelectionModel().clearSelection();  // Pulisce la selezione dell'orario
        comboFrequenza.getSelectionModel().clearSelection();
        txtClienteEmail.clear();
    }
}