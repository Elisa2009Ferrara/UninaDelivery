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
    private ComboBox<String> cbCorriere;
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
        List<Integer> ordiniDisponibili = ordineDAO.getOrdiniNonAssegnati();
        cbIdOrdine.setItems(FXCollections.observableArrayList(ordiniDisponibili));

        List<String> nomiCorrieri = corriereDAO.getNomiCorrieriDisponibili();
        cbCorriere.setItems(FXCollections.observableArrayList(nomiCorrieri));

        List<String> mezziDisponibili = mezzoTrasportoDAO.getMezziDisponibili();
        cbMezzoTrasporto.setItems(FXCollections.observableArrayList(mezziDisponibili));

        btnConferma.setOnAction(event -> creaSpedizione());
        btnCancella.setOnAction(event -> chiudiFinestra());
    }

    private void creaSpedizione() {
        if (cbIdOrdine.getValue() == null || dpArrivoPrevisto.getValue() == null ||
                tfSocieta.getText().isEmpty() || cbCorriere.getValue() == null ||
                cbMezzoTrasporto.getValue() == null) {
            mostraErrore("Compila tutti i campi!");
            return;
        }

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

        String nomeCorriere = cbCorriere.getValue();
        String numeroTelefonoCorriere = corriereDAO.getNumeroTelefonoByNome(nomeCorriere);

        if (numeroTelefonoCorriere == null) {
            mostraErrore("Errore: impossibile trovare il numero di telefono per " + nomeCorriere);
            return;
        }

        String mezzoTrasporto = cbMezzoTrasporto.getValue();

        Spedizione nuovaSpedizione = new Spedizione(
                0, "Destinazione non specificata", arrivoPrevisto, societa,
                "ordinato", LocalDate.now(), idOrdine, numeroTelefonoCorriere, mezzoTrasporto
        );

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
