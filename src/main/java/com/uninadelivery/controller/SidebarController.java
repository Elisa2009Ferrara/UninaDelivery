package com.uninadelivery.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SidebarController {

    private MainController mainController;

    @FXML
    private VBox sidebar;
    @FXML
    private HBox homeButton, orderButton, deliveryButton, statsButton, storeButton;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleNavigation(javafx.scene.input.MouseEvent event) {
        if (mainController == null) return;

        if (event.getSource() == homeButton) {
            mainController.loadContent("/com/uninadelivery/view/home.fxml");
        } else if (event.getSource() == orderButton) {
            mainController.loadContent("/com/uninadelivery/view/ordini.fxml");
        } else if (event.getSource() == deliveryButton) {
            mainController.loadContent("/com/uninadelivery/view/spedizioni.fxml");
        } else if (event.getSource() == statsButton) {
            mainController.loadContent("/com/uninadelivery/view/stats.fxml");
        } else if (event.getSource() == storeButton) {
            mainController.loadContent("/com/uninadelivery/view/magazzino.fxml");
        }
    }
}
