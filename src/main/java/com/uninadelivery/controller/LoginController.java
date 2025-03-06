package com.uninadelivery.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.File;
import java.util.Objects;
import java.util.ResourceBundle;
import java.net.URL;

import com.uninadelivery.model.dao.OperatoreDAO;
import com.uninadelivery.model.entities.Operatore;

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

    private final OperatoreDAO operatoreDAO = new OperatoreDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        brandingImageView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/LoginBanner.png")).toExternalForm()));
        userImageView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/images/LogoLogin.png")).toExternalForm()));
    }

    @FXML
    private void loginButtonOnAction(ActionEvent event) {
        if (!usernameTextField.getText().isBlank() && !passwordTextField.getText().isBlank()) {
            if (validateLogin(usernameTextField.getText(), passwordTextField.getText())) {
                loginMessageLabel.setText("Accesso riuscito!");
                // Apertura nuova finestra
            } else {
                loginMessageLabel.setText("Username o password errati.");
            }
        } else {
            loginMessageLabel.setText("Per favore, inserisci un username e una password.");
        }
    }

    private boolean validateLogin(String email, String password) {
        Operatore operatore = operatoreDAO.getOperatoreByEmailPassword(email, password);
        return operatore != null;
    }
}
