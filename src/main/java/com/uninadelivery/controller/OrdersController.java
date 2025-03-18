package com.uninadelivery.controller;

import com.uninadelivery.model.dao.OrdineDAO;
import com.uninadelivery.model.entities.Ordine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class OrdersController {

    @FXML
    private TableView<Ordine> tableView;

    @FXML
    private TableColumn<Ordine, Integer> idOrdine;

    @FXML
    private TableColumn<Ordine, String> emailUtente;

    @FXML
    private TableColumn<Ordine, LocalDate> dataOrdine;

    @FXML
    private TextField emailField;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Button searchButton;

    private ObservableList<Ordine> ordiniList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Imposta i valori nelle colonne
        idOrdine.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getIdOrdine()));
        emailUtente.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getEmailCliente()));
        dataOrdine.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getDataOrdine()));

        // Carica gli ordini all'avvio
        loadOrders();

        // Aggiunge il comportamento al bottone di ricerca
        searchButton.setOnAction(event -> searchOrders());
    }

    private void loadOrders() {
        OrdineDAO ordineDAO = new OrdineDAO();
        List<Ordine> allOrders = ordineDAO.getAllOrdini();
        ordiniList.setAll(allOrders);
        tableView.setItems(ordiniList);
    }

    private void searchOrders() {
        String email = emailField.getText().trim();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        List<Ordine> filteredOrders = ordiniList.stream()
                .filter(order -> (email.isEmpty() || order.getEmailCliente().equalsIgnoreCase(email))) // Filtra per email se inserita
                .filter(order -> (startDate == null || !order.getDataOrdine().isBefore(startDate))) // Controlla la data inizio
                .filter(order -> (endDate == null || !order.getDataOrdine().isAfter(endDate))) // Controlla la data fine
                .collect(Collectors.toList());

        tableView.setItems(FXCollections.observableArrayList(filteredOrders));
    }
}