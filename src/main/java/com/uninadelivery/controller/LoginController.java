package com.uninadelivery.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import com.uninadelivery.model.dao.OperatoreDAO;
import com.uninadelivery.model.entities.Operatore;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.File;
import java.util.Objects;
import java.util.ResourceBundle;
import java.net.URL;

public class LoginController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());

    @FXML
    private Label loginMessageLabel;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private ImageView userImageView;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label registerMessageLabel;
    @FXML
    private Button loginButton;


    private final OperatoreDAO operatoreDAO = new OperatoreDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        brandingImageView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/LoginBanner.png")).toExternalForm()));
        userImageView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/LogoLogin.png")).toExternalForm()));

        loginMessageLabel.setOpacity(0); // Nasconde il messaggio di errore inizialmente
    }

    @FXML
    private void loginButtonOnAction(ActionEvent event) {
        // Verifica se i campi non sono vuoti
        if (!usernameTextField.getText().isBlank() && !passwordTextField.getText().isBlank()) {
            if (validateLogin(usernameTextField.getText(), passwordTextField.getText())) {
                // Login riuscito
                loginMessageLabel.setText("Accesso riuscito!");
                loginMessageLabel.setStyle("-fx-background-color: #dff0d8; -fx-text-fill: #3c763d;"); // Verde per successo
                loginMessageLabel.setOpacity(1); // Rende visibile il messaggio
                // Aprire una nuova finestra
            } else {
                // Login fallito
                loginMessageLabel.setText("Username o password errati.");
                loginMessageLabel.setStyle("-fx-background-color: #f2dede; -fx-text-fill: #cf0000;");
                loginMessageLabel.setOpacity(1); // Rende visibile il messaggio
            }
        } else {
            // Se uno dei campi è vuoto
            loginMessageLabel.setText("Per favore, inserisci username e password.");
            loginMessageLabel.setStyle("-fx-background-color: #f2dede; -fx-text-fill: #cf0000;");
            loginMessageLabel.setOpacity(1); // Rende visibile il messaggio
        }
    }

    private boolean validateLogin(String email, String password) {
        Operatore operatore = operatoreDAO.getOperatoreByEmailPassword(email,password);
        return operatore != null;
    }

    @FXML
    public void setOnMouseClick(MouseEvent event) {
        System.out.println("Passaggio alla schermata di registrazione...");
        createAccountForm();
    }

    public void createAccountForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/uninadelivery/view/registration.fxml"));
            Parent root = loader.load();

            // Ottieni la scena attuale
            Stage currentStage = (Stage) loginButton.getScene().getWindow();

            // Imposta il nuovo contenuto della scena
            currentStage.setScene(new Scene(root, 525, 407));

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Errore durante il caricamento della schermata di registrazione", e);
        }
    }

    private void goToRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/uninadelivery/view/Register.fxml"));
            Parent root = loader.load();

            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();

            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}