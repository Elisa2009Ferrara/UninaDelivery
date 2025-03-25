package com.uninadelivery.controller;

import com.uninadelivery.model.dao.OrdineDAO; // Usa direttamente il DAO
import com.uninadelivery.model.dao.SpedizioneDAO;
import com.uninadelivery.model.entities.Ordine;
import com.uninadelivery.model.entities.Spedizione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


import java.time.LocalDate;
import java.util.List;

public class CreateDeliveryController {

    @FXML
    private TableView<Ordine> tableView;
    @FXML
    private TableColumn<Ordine, Integer> colIdOrdine;
    @FXML
    private TableColumn<Ordine, String> colNome;
    @FXML
    private TableColumn<Ordine, String> colCognome;
    @FXML
    private ComboBox<String> comboMezzoTrasporto;
    @FXML
    private ComboBox<String> comboCorriere;
    @FXML
    private Button btnCrea;

    private ObservableList<Ordine> ordiniList = FXCollections.observableArrayList();
    private ObservableList<String> mezziTrasportoList = FXCollections.observableArrayList("Camion", "Furgone", "Moto", "Aereo");
    private ObservableList<String> corrieriList = FXCollections.observableArrayList("DHL", "UPS", "FedEx", "Bartolini");

    private OrdineDAO ordineDAO = new OrdineDAO(); // Usa direttamente il DAO
    private SpedizioneDAO spedizioneDAO = new SpedizioneDAO();

    @FXML
    public void initialize() {
        // Associa le colonne agli attributi di Ordine
        colIdOrdine.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdOrdine()).asObject());
        colNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmailCliente()));
        colCognome.setCellValueFactory(cellData -> new SimpleStringProperty(""));


        // Carica gli ordini dal database
        caricaOrdini();

        // Popola le ComboBox
        comboMezzoTrasporto.setItems(mezziTrasportoList);
        comboCorriere.setItems(corrieriList);

        // Gestisce la creazione della spedizione
        btnCrea.setOnAction(event -> aggiornaOrdineConSpedizione());
    }

    private void caricaOrdini() {
        List<Ordine> ordiniDalDB = ordineDAO.getOrdiniSenzaSpedizione(); // Usa OrdineDAO
        ordiniList.clear();
        ordiniList.addAll(ordiniDalDB);
        tableView.setItems(ordiniList);
    }

    private void aggiornaOrdineConSpedizione() {
        Ordine ordineSelezionato = tableView.getSelectionModel().getSelectedItem();
        if (ordineSelezionato == null) {
            mostraErrore("Seleziona un ordine prima di assegnare il mezzo e il corriere!");
            return;
        }

        String mezzoSelezionato = comboMezzoTrasporto.getValue();
        String corriereSelezionato = comboCorriere.getValue();

        if (mezzoSelezionato == null || corriereSelezionato == null) {
            mostraErrore("Seleziona un mezzo di trasporto e un corriere!");
            return;
        }

        // Crea una nuova spedizione e la salva nel database
        Spedizione nuovaSpedizione = new Spedizione(
                ordineSelezionato.getIdOrdine(),
                "Default",  // Valore fittizio per la destinazione
                LocalDate.now().plusDays(3),  // Data fittizia di arrivo
                corriereSelezionato,
                "In preparazione",  // Stato fittizio
                LocalDate.now()
        );

        spedizioneDAO.createSpedizione(nuovaSpedizione);

        // Ricarica la tabella per rimuovere l'ordine aggiornato
        caricaOrdini();
    }

    private void mostraErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}

