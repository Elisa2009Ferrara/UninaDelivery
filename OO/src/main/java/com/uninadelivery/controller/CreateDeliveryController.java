package com.uninadelivery.controller;

import com.uninadelivery.model.dao.MezzoDiTrasportoDAO;
import com.uninadelivery.model.dao.OrdineDAO;
import com.uninadelivery.model.dao.SpedizioneDAO;
import com.uninadelivery.model.dao.CorriereDAO;
import com.uninadelivery.model.entities.Spedizione;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class CreateDeliveryController {

    @FXML
    private ComboBox<Integer> cbIdOrdine;

    @FXML
    private DatePicker dpArrivoPrevisto;

    @FXML
    private TextField tfSocieta;

    @FXML
    private ComboBox<String> cbCorriere;  // Cambiato da Corriere a String

    @FXML
    private ComboBox<String> cbMezzoTrasporto;

    @FXML
    private Button btnConferma;

    @FXML
    private Button btnCancella;

    private final SpedizioneDAO spedizioneDAO = new SpedizioneDAO();
    private final OrdineDAO ordineDAO = new OrdineDAO();
    private final CorriereDAO corriereDAO = new CorriereDAO();
    private final MezzoDiTrasportoDAO mezzoTrasportoDAO = new MezzoDiTrasportoDAO();

    @FXML
    public void initialize() {
        // Carica ID Ordini non assegnati
        List<Integer> ordiniDisponibili = ordineDAO.getOrdiniNonAssegnati();
        cbIdOrdine.setItems(FXCollections.observableArrayList(ordiniDisponibili));

        // Carica i corrieri disponibili (solo nome e cognome)
        List<String> nomiCorrieri = corriereDAO.getNomiCorrieriDisponibili(); // Metodo DAO che restituisce List<String>
        cbCorriere.setItems(FXCollections.observableArrayList(nomiCorrieri));

        // Carica Mezzi di Trasporto disponibili
        List<String> mezziDisponibili = mezzoTrasportoDAO.getMezziDisponibili();
        cbMezzoTrasporto.setItems(FXCollections.observableArrayList(mezziDisponibili));

        // Pulsante "Conferma"
        btnConferma.setOnAction(event -> creaSpedizione());

        // Pulsante "Cancella"
        btnCancella.setOnAction(event -> chiudiFinestra());
    }

    private void creaSpedizione() {
        if (cbIdOrdine.getValue() == null || dpArrivoPrevisto.getValue() == null ||
                tfSocieta.getText().isEmpty() || cbCorriere.getValue() == null ||
                cbMezzoTrasporto.getValue() == null) {
            mostraErrore("Compila tutti i campi!");
            return;
        }

        // Mostra finestra di conferma
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma Creazione Spedizione");
        alert.setHeaderText(null);
        alert.setContentText("Sei sicuro di voler creare questa spedizione?");

        ButtonType buttonConferma = new ButtonType("Conferma");
        ButtonType buttonAnnulla = new ButtonType("Annulla", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonConferma, buttonAnnulla);

        alert.showAndWait().ifPresent(response -> {
            if (response == buttonConferma) {
                salvaSpedizione();
            }
        });
    }

    private void salvaSpedizione() {
        int idOrdine = cbIdOrdine.getValue();
        LocalDate arrivoPrevisto = dpArrivoPrevisto.getValue();
        String societa = tfSocieta.getText();

        // Prendi il nome del corriere selezionato dalla ComboBox
        String nomeCorriere = cbCorriere.getValue();

        // Ottieni il numero di telefono del corriere dal DAO
        String numeroTelefonoCorriere = corriereDAO.getNumeroTelefonoByNome(nomeCorriere);

        if (numeroTelefonoCorriere == null) {
            mostraErrore("Errore: impossibile trovare il numero di telefono per " + nomeCorriere);
            return;
        }

        // Ottieni il mezzo di trasporto selezionato dalla ComboBox
        String mezzoTrasporto = cbMezzoTrasporto.getValue();

        // Crea la nuova spedizione con tutti i parametri
        Spedizione nuovaSpedizione = new Spedizione(
                0,
                "Destinazione non specificata",  // Puoi modificare se necessario
                arrivoPrevisto,
                societa,
                "ordinato",
                LocalDate.now(),
                idOrdine,
                nomeCorriere,
                mezzoTrasporto
        );

        // Salva la spedizione nel database
        boolean successo = spedizioneDAO.createSpedizione(nuovaSpedizione);
        if (successo) {
            mostraMessaggio("Spedizione creata con successo!");
            chiudiFinestra();
        } else {
            mostraErrore("Errore nella creazione della spedizione.");
        }
    }


    private void mostraMessaggio(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Operazione completata");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    private void mostraErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    private void chiudiFinestra() {
        Stage stage = (Stage) btnCancella.getScene().getWindow();
        stage.close();
    }
}
