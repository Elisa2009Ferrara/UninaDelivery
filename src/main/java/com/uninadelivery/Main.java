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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/uninadelivery/view/login.fxml"));
        Parent root = loader.load();

        // Creazione della scena
        Scene scene = new Scene(root, 525, 407);

        // Collegamento file CSS
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());

        // Creazione dello stage e titolo della finestra
        primaryStage.setTitle("UninaDelivery");
        primaryStage.setScene(scene);  // Qui usiamo la scena creata sopra

        // Settiamo l'icona
        Image appIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/IconaApp.png")));
        primaryStage.getIcons().add(appIcon);

        // Finestra non ridimensionabile
        primaryStage.setResizable(false);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}