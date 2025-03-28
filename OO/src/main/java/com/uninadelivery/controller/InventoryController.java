package com.uninadelivery.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.uninadelivery.model.dao.ProdottoDAO;
import com.uninadelivery.model.entities.Prodotto;

import java.util.List;

public class InventoryController {

    @FXML
    private TableView<Prodotto> tableView;
    @FXML
    private TableColumn<Prodotto, Integer> idColumn;
    @FXML
    private TableColumn<Prodotto, String> nameColumn;
    @FXML
    private TableColumn<Prodotto, String> sizeColumn;
    @FXML
    private TableColumn<Prodotto, Double> weightColumn;
    @FXML
    private TableColumn<Prodotto, Integer> quantityColumn;
    @FXML
    private TableColumn<Prodotto, Double> priceColumn;
    @FXML
    private TableColumn<Prodotto, Integer> storeColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Label lblId, lblName, lblSize, lblWeight, lblPrice;
    @FXML
    private Spinner<Integer> quantitySpinner;

    private ObservableList<Prodotto> productList = FXCollections.observableArrayList();
    private ProdottoDAO prodottoDAO = new ProdottoDAO();
    private Prodotto selectedProdotto;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idProdotto"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nomeProdotto"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("dimensioni"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("peso"));
        storeColumn.setCellValueFactory(new PropertyValueFactory<>("magazzino"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantitaDisp"));

        loadDataFromDatabase();

        tableView.setItems(productList);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterProducts(newValue));
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showProductDetails(newValue));

        // Configurazione dello Spinner
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0);
        quantitySpinner.setValueFactory(valueFactory);
        quantitySpinner.valueProperty().addListener((obs, oldValue, newValue) -> updateQuantity(newValue));
    }

    private void loadDataFromDatabase() {
        List<Prodotto> prodotti = prodottoDAO.getAllProdotti();
        productList.setAll(prodotti);
    }

    private void filterProducts(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            tableView.setItems(productList);
            return;
        }

        ObservableList<Prodotto> filteredList = FXCollections.observableArrayList();
        for (Prodotto prodotto : productList) {
            if (prodotto.getNomeProdotto().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(prodotto);
            }
        }
        tableView.setItems(filteredList);
    }

    private void showProductDetails(Prodotto prodotto) {
        if (prodotto != null) {
            this.selectedProdotto = prodotto;

            lblId.setText(String.valueOf(prodotto.getIdProdotto()));
            lblName.setText(prodotto.getNomeProdotto());
            lblSize.setText(prodotto.getDimensioni());
            lblWeight.setText(String.valueOf(prodotto.getPeso()));
            lblPrice.setText(String.format("%.2f", prodotto.getPrezzo()));
            quantitySpinner.getValueFactory().setValue(prodotto.getQuantitaDisp());

            // Tooltip: mostra il nome completo del prodotto se è troppo lungo
            Tooltip tooltip = new Tooltip(prodotto.getNomeProdotto());
            Tooltip.install(lblName, tooltip);
        }
    }

    private void updateQuantity(int newQuantity) {
        if (selectedProdotto != null) {
            selectedProdotto.setQuantitaDisp(newQuantity);
            tableView.refresh();

            prodottoDAO.updateQuantita(selectedProdotto.getIdProdotto(), newQuantity);
        }
    }
}
