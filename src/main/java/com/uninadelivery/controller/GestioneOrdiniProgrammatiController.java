package com.uninadelivery.controller;

import com.uninadelivery.model.entities.Programmazione;
import com.uninadelivery.model.dao.ProgrammazioneDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class GestioneOrdiniProgrammatiController {

    @FXML private TableView<Programmazione> tableOrdini;
    @FXML private TableColumn<Programmazione, Integer> colIdOrdine;
    @FXML private TableColumn<Programmazione, LocalDate> colDataSpedizione;
    @FXML private TableColumn<Programmazione, String> colFrequenza;
    @FXML private TableColumn<Programmazione, String> colClienteEmail;
    @FXML private Button btnModifica;
    @FXML private Button btnElimina;
    @FXML private Button btnAggiorna;

    private ProgrammazioneDAO programmazioneDAO = new ProgrammazioneDAO();  // Istanza non statica
    private ObservableList<Programmazione> listaOrdini = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Usa i metodi Property per configurare le TableColumn
        colIdOrdine.setCellValueFactory(cellData -> cellData.getValue().idProgrammazioneProperty().asObject());
        colDataSpedizione.setCellValueFactory(cellData -> cellData.getValue().proxConsegnaProperty());
        colFrequenza.setCellValueFactory(cellData -> cellData.getValue().frequenzaProperty());
        colClienteEmail.setCellValueFactory(cellData -> cellData.getValue().clienteEmailProperty());

        Scene scene = tableOrdini.getScene(); // Ottieni la scena associata alla TableView
        //scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        String css = this.getClass().getResource("/style.css").toExternalForm();
        scene.getStylesheets().add(css);

        tableOrdini.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        caricaOrdiniProgrammati();
    }

    private void caricaOrdiniProgrammati() {
        listaOrdini.clear();
        try {
            listaOrdini.addAll(programmazioneDAO.getAllOrdiniProgrammati());  // Chiamata istanziata
            tableOrdini.setItems(listaOrdini);
        } catch (SQLException e) {
            mostraErrore("Errore nel caricamento degli ordini programmati.");
        }
    }

    @FXML
    private void modificaOrdine(ActionEvent event) {
        Programmazione ordineSelezionato = tableOrdini.getSelectionModel().getSelectedItem();

        if (ordineSelezionato == null) {
            mostraErrore("Seleziona un ordine da modificare.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog(ordineSelezionato.getProxConsegna().toString());
        dialog.setTitle("Modifica Data Spedizione");
        dialog.setHeaderText("Modifica la data della spedizione");
        dialog.setContentText("Inserisci la nuova data (YYYY-MM-DD):");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newDate -> {
            try {
                LocalDate nuovaData = LocalDate.parse(newDate);
                ordineSelezionato.setProxConsegna(nuovaData);
                programmazioneDAO.aggiornaOrdineProgrammato(ordineSelezionato);  // Chiamata istanziata
                caricaOrdiniProgrammati();
            } catch (Exception e) {
                mostraErrore("Data non valida.");
            }
        });
    }

    @FXML
    private void eliminaOrdine(ActionEvent event) {
        Programmazione ordineSelezionato = tableOrdini.getSelectionModel().getSelectedItem();

        if (ordineSelezionato == null) {
            mostraErrore("Seleziona un ordine da eliminare.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Vuoi eliminare l'ordine?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                programmazioneDAO.eliminaOrdineProgrammato(ordineSelezionato.getIdProgrammazione());  // Chiamata istanziata
                caricaOrdiniProgrammati();
            } catch (SQLException e) {
                mostraErrore("Errore durante l'eliminazione.");
            }
        }
    }

    @FXML
    private void aggiornaLista(ActionEvent event) {
        caricaOrdiniProgrammati();
    }

    private void mostraErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}