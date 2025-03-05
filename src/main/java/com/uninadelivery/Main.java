package com.uninadelivery;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

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
        primaryStage.setScene(new Scene(root, 332, 400));

        // Settiamo l'icona
        primaryStage.getIcons().add( new Image( "/images/UninaIconaNuova.png" ) );

        primaryStage.show();
    }
    public static void main(String[] args) { launch(args); }
}
