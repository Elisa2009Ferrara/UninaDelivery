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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/uninadelivery/view/login.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 525, 407);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());

        primaryStage.setTitle("UninaDelivery");
        primaryStage.setScene(scene);

        Image appIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/IconaApp.png")));
        primaryStage.getIcons().add(appIcon);

        primaryStage.setResizable(false); // Login e registrazione rimangono fissi

        primaryStage.show();
    }

}