package com.uninadelivery.controller;

import com.uninadelivery.model.dao.SpedizioneDAO;
import com.uninadelivery.model.entities.Spedizione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DeliveryController {

    private static final Logger LOGGER = Logger.getLogger(DeliveryController.class.getName());

    @FXML
    private Button btnCrea;

    @FXML
    private TableView<Spedizione> tableView;

    @FXML
    private TableColumn<Spedizione, Integer> colIdSpedizione;

    @FXML
    private TableColumn<Spedizione, Integer> colIdOrdine;

    @FXML
    private TableColumn<Spedizione, String> colSocieta;

    @FXML
    private TableColumn<Spedizione, String> colStato;

    @FXML
    private TableColumn<Spedizione, String> colCorriere;

    @FXML
    private TableColumn<Spedizione, String> colMezzoTrasporto;

    private final SpedizioneDAO spedizioneDAO = new SpedizioneDAO();

    @FXML
    public void initialize() {
        colIdSpedizione.setCellValueFactory(new PropertyValueFactory<>("idSpedizione"));
        colIdOrdine.setCellValueFactory(new PropertyValueFactory<>("idOrdine"));
        colSocieta.setCellValueFactory(new PropertyValueFactory<>("societa"));
        colStato.setCellValueFactory(new PropertyValueFactory<>("stato"));
        colCorriere.setCellValueFactory(new PropertyValueFactory<>("corriere"));
        colMezzoTrasporto.setCellValueFactory(new PropertyValueFactory<>("mezzoTrasporto"));

        loadTableData();

        btnCrea.setOnAction(event -> openCreateDeliveryScreen());
    }

    private void loadTableData() {
        List<Spedizione> spedizioni = spedizioneDAO.getAllSpedizioni();
        ObservableList<Spedizione> spedizioniObservable = FXCollections.observableArrayList(spedizioni);
        tableView.setItems(spedizioniObservable);
    }

    // Metodo per aprire la schermata "createDelivery"
    private void openCreateDeliveryScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/uninadelivery/view/CreateDelivery.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Crea nuova spedizione");

            stage.setOnHidden(event -> loadTableData());

            stage.show();

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Errore durante l'apertura della schermata di creazione", e);
        }
    }
}
