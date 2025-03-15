package com.uninadelivery.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {

    @FXML
    private BorderPane borderPane;
    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {
        try {
            // Carica la sidebar
            FXMLLoader sidebarLoader = new FXMLLoader(getClass().getResource("/com/uninadelivery/view/sidebar.fxml"));
            Node sidebar = sidebarLoader.load();
            borderPane.setLeft(sidebar);

            // Ottieni il controller della sidebar e passa il riferimento al MainController
            SidebarController sidebarController = sidebarLoader.getController();
            sidebarController.setMainController(this);

            // Carica la home di default
            loadContent("/com/uninadelivery/view/home.fxml");

        } catch (IOException e) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, "Errore nel caricamento della sidebar o della home", e);
        }
    }

    // Metodo per aggiornare la schermata nella content area
    public void loadContent(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node content = loader.load();
            contentArea.getChildren().setAll(content); // Sostituisce il contenuto attuale
        } catch (IOException e) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, "Errore nel caricamento del contenuto: " + fxmlPath, e);
        }
    }
}