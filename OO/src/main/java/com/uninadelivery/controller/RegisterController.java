package com.uninadelivery.controller;

import com.uninadelivery.model.dao.OperatoreDAO;
import com.uninadelivery.model.entities.Operatore;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegisterController {

    private static final Logger LOGGER = Logger.getLogger(RegisterController.class.getName());

    @FXML
    private ImageView backButtonView;

    @FXML
    private TextField EmailTextField;
    @FXML
    private PasswordField setPasswordField;
    @FXML
    private TextField NomeTextField;
    @FXML
    private TextField CognomeTextField;
    @FXML
    private TextField NumeroTelTextField;
    @FXML
    private PasswordField confirmPasswordField;

    private final OperatoreDAO operatoreDAO = new OperatoreDAO();

    @FXML
    public void initialize() {
        backButtonView.setOnMouseClicked(event -> goToLogin());
    }

    @FXML
    private void handleRegistration() {
        String email = EmailTextField.getText();
        String password = setPasswordField.getText();
        String confermaPassword = confirmPasswordField.getText();
        String nome = NomeTextField.getText();
        String cognome = CognomeTextField.getText();
        String telefono = NumeroTelTextField.getText();

        if (email.isEmpty() || password.isEmpty() || confermaPassword.isEmpty() || nome.isEmpty() || cognome.isEmpty() || telefono.isEmpty()) {
            showAlert("Errore", "Tutti i campi devono essere compilati!", Alert.AlertType.ERROR);
            return;
        }

        if (!password.equals(confermaPassword)) {
            showAlert("Errore", "Le password non coincidono!", Alert.AlertType.ERROR);
            return;
        }

        Operatore nuovoOperatore = new Operatore(email, password, nome, cognome, telefono);
        boolean registrato = operatoreDAO.registerOperatore(nuovoOperatore);

        if (registrato) {
            showAlert("Registrazione riuscita!", "Ora puoi accedere con le tue credenziali.", Alert.AlertType.INFORMATION);
            goToLogin();
        } else {
            showAlert("Errore", "Registrazione fallita. L'email potrebbe essere già in uso.", Alert.AlertType.ERROR);
        }
    }

    private void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/uninadelivery/view/Login.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) backButtonView.getScene().getWindow();
            currentStage.close();

            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(root));
            loginStage.show();

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Errore nel caricamento della schermata di login", e);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}