package com.uninadelivery.controller;

import com.uninadelivery.model.entities.Ordine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import com.uninadelivery.database.DatabaseHelper;


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

    @FXML
    public void initialize() {
        // Associa le colonne agli attributi di Ordine
        colIdOrdine.setCellValueFactory(cellData -> javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getIdOrdine()).asObject());
        colNome.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmailCliente())); // Supponiamo che nome sia l'email
        colCognome.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty("")); // Da cambiare se hai un campo cognome
        colIdOrdine.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdOrdine()).asObject());
        colNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmailCliente()));


        // Carica gli ordini dal database
        caricaOrdini();

        // Popola le ComboBox
        comboMezzoTrasporto.setItems(mezziTrasportoList);
        comboCorriere.setItems(corrieriList);

        // Gestisce la creazione della spedizione
        btnCrea.setOnAction(event -> aggiornaOrdineConSpedizione());
    }

    private void caricaOrdini() {
        // Query al database per recuperare gli ordini senza spedizione assegnata
        List<Ordine> ordiniDalDB = DatabaseHelper.getOrdiniSenzaSpedizione(); // Questo metodo va implementato

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

        // Aggiorna il database con mezzo di trasporto e corriere
        DatabaseHelper.aggiornaOrdine(ordineSelezionato.getIdOrdine(), mezzoSelezionato, corriereSelezionato);

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

