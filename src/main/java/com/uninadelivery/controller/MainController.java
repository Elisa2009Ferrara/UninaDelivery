package com.uninadelivery.controller;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Parent sidebar = FXMLLoader.load(getClass().getResource("/com/uninadelivery/view/sidebar.fxml"));
            Parent contentArea = FXMLLoader.load(getClass().getResource("/com/uninadelivery/view/contentArea.fxml"));
            borderPane.setLeft(sidebar);
            borderPane.setCenter(contentArea);

        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
