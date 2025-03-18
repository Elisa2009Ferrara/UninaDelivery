package com.uninadelivery.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {

    @FXML
    public VBox sidebarContainer;
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

            // Content area adattata alla finestra
            borderPane.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
            contentArea.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);

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
            Parent view = loader.load();

            // Allinea al centro e assicura l'adattamento alla finestra
            StackPane.setAlignment(view, Pos.CENTER);
            StackPane.setMargin(view, new Insets(10));

            // Se la view è una Region (VBox, HBox, StackPane, BorderPane, ecc.), la rendiamo adattabile
            if (view instanceof javafx.scene.layout.Region) {
                ((javafx.scene.layout.Region) view).prefWidthProperty().bind(contentArea.widthProperty());
                ((javafx.scene.layout.Region) view).prefHeightProperty().bind(contentArea.heightProperty());
            }

            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, "Errore nel caricamento del contenuto: " + fxmlPath, e);
        }
    }

    public void setCenterView(Parent view) {
        StackPane.setAlignment(view, Pos.CENTER);

        // Controlla se la view è un Region per poter adattare le dimensioni
        if (view instanceof Region) {
            Region regionView = (Region) view;
            regionView.prefWidthProperty().bind(contentArea.widthProperty());
            regionView.prefHeightProperty().bind(contentArea.heightProperty());
        }

        contentArea.getChildren().setAll(view);
    }
}