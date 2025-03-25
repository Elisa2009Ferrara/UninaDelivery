package com.uninadelivery.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.util.Callback;

public class CreateDeliveryController {

    @FXML private TableView<Order> tableView;
    @FXML private TableColumn<Order, Boolean> colSeleziona;
    @FXML private TableColumn<Order, Integer> colIdOrdine;
    @FXML private TableColumn<Order, String> colNome;
    @FXML private TableColumn<Order, String> colCognome;
    @FXML private ComboBox<String> comboMezzoTrasporto;
    @FXML private ComboBox<String> comboCorriere;
    @FXML private Button btnCrea;

    private final ObservableList<Order> ordini = FXCollections.observableArrayList();
    private final ObservableList<String> mezziTrasporto = FXCollections.observableArrayList("Furgone", "Moto", "Bicicletta");
    private final ObservableList<String> corrieri = FXCollections.observableArrayList("Mario Rossi", "Luca Bianchi", "Anna Verdi");

    @FXML
    public void initialize() {
        // Configura le colonne della tabella
        colIdOrdine.setCellValueFactory(new PropertyValueFactory<>("idOrdine"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
        colCognome.setCellValueFactory(new PropertyValueFactory<>("cognomeCliente"));

        // Configura la colonna delle checkbox
        colSeleziona.setCellValueFactory(param -> param.getValue().selezionatoProperty());
        colSeleziona.setCellFactory(CheckBoxTableCell.forTableColumn(colSeleziona));

        // Aggiungi dati di esempio alla tabella
        ordini.add(new Order(1, "Giovanni", "Esposito"));
        ordini.add(new Order(2, "Francesca", "Russo"));
        ordini.add(new Order(3, "Lorenzo", "Bianchi"));

        tableView.setItems(ordini);

        // Popola i ComboBox
        comboMezzoTrasporto.setItems(mezziTrasporto);
        comboCorriere.setItems(corrieri);

        // Azione bottone "Crea"
        btnCrea.setOnAction(event -> creaSpedizione());
    }

    private void creaSpedizione() {
        Order ordineSelezionato = tableView.getItems().stream()
                .filter(Order::isSelezionato)
                .findFirst()
                .orElse(null);

        if (ordineSelezionato == null) {
            showAlert("Errore", "Seleziona almeno un ordine!");
            return;
        }

        String mezzo = comboMezzoTrasporto.getValue();
        String corriere = comboCorriere.getValue();

        if (mezzo == null || corriere == null) {
            showAlert("Errore", "Seleziona un mezzo di trasporto e un corriere!");
            return;
        }

        System.out.println("Spedizione creata per Ordine #" + ordineSelezionato.getIdOrdine() +
                " con " + corriere + " su " + mezzo);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}