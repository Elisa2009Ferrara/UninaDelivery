package com.uninadelivery.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private TextField searchField;

    @FXML
    private Label lblId, lblName, lblSize, lblWeight, lblQuantity, lblPrice;

    private ObservableList<Prodotto> productList = FXCollections.observableArrayList();
    private ObservableList<Prodotto> filteredList = FXCollections.observableArrayList();
    private ProdottoDAO prodottoDAO = new ProdottoDAO();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idProdotto"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nomeProdotto"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("dimensioni"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("peso"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantitaDisp"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("prezzo"));

        loadDataFromDatabase();

        tableView.setItems(productList);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterProducts(newValue));
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showProductDetails(newValue));
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

        filteredList.clear();
        for (Prodotto prodotto : productList) {
            if (prodotto.getNomeProdotto().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(prodotto);
            }
        }
        tableView.setItems(filteredList);
    }

    private void showProductDetails(Prodotto prodotto) {
        if (prodotto != null) {
            lblId.setText(String.valueOf(prodotto.getIdProdotto()));
            lblName.setText(prodotto.getNomeProdotto());
            lblSize.setText(prodotto.getDimensioni());
            lblWeight.setText(String.valueOf(prodotto.getPeso()));
            lblPrice.setText(String.format("%.2f", prodotto.getPrezzo()));
            lblQuantity.setText(String.valueOf(prodotto.getQuantitaDisp()));
        }
    }
}
