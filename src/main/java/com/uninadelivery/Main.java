package com.uninadelivery;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load del file FXML che contiene tutti gli UI elements
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/uninadelivery/view/login.fxml"));
        Parent root = loader.load();

        // Creazione dello stage e titolo della window
        primaryStage.setTitle("UninaDelivery");

        // Settiamo la scena usando root, con la larghezza e altezza specificate
        primaryStage.setScene(new Scene(root, 560, 407));

        // Settiamo l'icona
        Image appIcon = new Image (Objects.requireNonNull(getClass().getResourceAsStream("/images/IconaApp.png")));
        primaryStage.getIcons().add(appIcon);

        primaryStage.show();
    }
    public static void main(String[] args) { launch(args); }
}
