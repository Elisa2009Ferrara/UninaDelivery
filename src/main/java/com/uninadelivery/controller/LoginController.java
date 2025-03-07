package com.uninadelivery.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import com.uninadelivery.model.dao.OperatoreDAO;
import com.uninadelivery.model.entities.Operatore;
import org.w3c.dom.events.MouseEvent;

import java.io.File;
import java.util.Objects;
import java.util.ResourceBundle;
import java.net.URL;

public class LoginController implements Initializable {

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

    private final OperatoreDAO operatoreDAO = new OperatoreDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        brandingImageView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/LoginBanner.png")).toExternalForm()));
        userImageView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/LogoLogin.png")).toExternalForm()));

        loginMessageLabel.setOpacity(0); // Nasconde il messaggio di errore inizialmente

        registerMessageLabel.setOnMouseClicked(event -> {
            // Aggiungi qui il codice per passare alla schermata di registrazione
            //openRegistrationScreen();  commentato per il momento perché non esiste
        });
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
        Operatore operatore = operatoreDAO.getOperatoreByEmailPassword(email, password);
        return operatore != null;
    }

    @FXML
    public void setOnMouseClick(javafx.scene.input.MouseEvent mouseEvent) {
        // Codice di cosa accade quando clicchi la label
    }
}