package com.uninadelivery.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController {

    @FXML public VBox sidebarContainer;
    @FXML private BorderPane borderPane;
    @FXML private StackPane contentArea;

    @FXML
    public void initialize() {
        try {
            FXMLLoader sidebarLoader = new FXMLLoader(getClass().getResource("/com/uninadelivery/view/sidebar.fxml"));
            Node sidebar = sidebarLoader.load();
            borderPane.setLeft(sidebar);

            SidebarController sidebarController = sidebarLoader.getController();
            sidebarController.setMainController(this);

            borderPane.widthProperty().addListener((obs, oldWidth, newWidth) -> {
                double width = newWidth.doubleValue();

                if (width < 600) {
                    sidebar.setStyle("-fx-pref-width: 150px; -fx-min-width: 150px;");
                } else {
                    sidebar.setStyle("-fx-pref-width: 220px; -fx-min-width: 200px;");
                }
            });

            loadContent("/com/uninadelivery/view/home.fxml");

        } catch (IOException e) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, "Errore nel caricamento della sidebar o della home", e);
        }
    }

    public void loadContent(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();

            StackPane.setAlignment(view, Pos.CENTER);

            if (view instanceof javafx.scene.layout.Region) {
                ((javafx.scene.layout.Region) view).prefWidthProperty().bind(contentArea.widthProperty());
                ((javafx.scene.layout.Region) view).prefHeightProperty().bind(contentArea.heightProperty());
            }

            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, "Errore nel caricamento del contenuto: " + fxmlPath, e);
        }
    }

}