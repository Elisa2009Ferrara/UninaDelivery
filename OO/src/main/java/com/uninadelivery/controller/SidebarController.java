package com.uninadelivery.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.scene.input.MouseEvent;

public class SidebarController {

    private MainController mainController;

    @FXML
    private VBox sidebar;
    @FXML
    private HBox homeButton, orderButton, deliveryButton, statsButton, storeButton;

    private HBox selectedButton = null;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleSidebarClick(MouseEvent event) {
        HBox clickedButton = (HBox) event.getSource();
        selectButton(clickedButton);

        // Cambia schermata in base al pulsante cliccato
        if (clickedButton == homeButton) {
            mainController.loadContent("/com/uninadelivery/view/home.fxml");
        } else if (clickedButton == orderButton) {
            mainController.loadContent("/com/uninadelivery/view/orders.fxml");
        } else if (clickedButton == deliveryButton) {
            mainController.loadContent("/com/uninadelivery/view/delivery.fxml");
        } else if (clickedButton == statsButton) {
            mainController.loadContent("/com/uninadelivery/view/stats.fxml");
        } else if (clickedButton == storeButton) {
            mainController.loadContent("/com/uninadelivery/view/store.fxml");
        }
    }

    private void selectButton(HBox button) {
        if (selectedButton != null) {
            selectedButton.getStyleClass().remove("sidebar-btn-selected");
        }
        button.getStyleClass().add("sidebar-btn-selected");
        selectedButton = button;
    }
}