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

    private final ObservableList<Ordine> ordiniList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Imposta i valori nelle colonne
        idOrdine.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getIdOrdine()));
        emailUtente.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getEmailCliente()));
        dataOrdine.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getDataOrdine()));

        loadOrders();
        searchButton.setOnAction(_ -> searchOrders());

        emailField.textProperty().addListener((_, _, _) -> searchOrders());
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
                .filter(order -> (email.isEmpty() || order.getEmailCliente().toLowerCase().contains(email.toLowerCase())))
                .filter(order -> (startDate == null || !order.getDataOrdine().isBefore(startDate)))
                .filter(order -> (endDate == null || !order.getDataOrdine().isAfter(endDate)))
                .collect(Collectors.toList());

        tableView.setItems(FXCollections.observableArrayList(filteredOrders));
    }
}