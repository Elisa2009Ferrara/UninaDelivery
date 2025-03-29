package com.uninadelivery.controller;

import com.uninadelivery.model.entities.Programmazione;
import com.uninadelivery.model.dao.ProgrammazioneDAO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.io.IOException;

public class OrganizeProgrammedOrdersController {

    @FXML private TableView<Programmazione> tableOrdini;
    @FXML private TableColumn<Programmazione, Integer> colIdOrdine;
    @FXML private TableColumn<Programmazione, LocalDate> colDataSpedizione;
    @FXML private TableColumn<Programmazione, String> colFrequenza;
    @FXML private TableColumn<Programmazione, String> colClienteEmail;
    @FXML private TableColumn<Programmazione, String> colOrario;
    @FXML private TableColumn<Programmazione, LocalDate> colDataFine;
    @FXML private TextField emailClienteField;

    private final ProgrammazioneDAO programmazioneDAO = new ProgrammazioneDAO();
    private final ObservableList<Programmazione> listaOrdini = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            if (tableOrdini.getScene() != null) {
                tableOrdini.getScene().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            } else {
                System.err.println("Attenzione: la Scene è ancora null. Lo stylesheet non è stato caricato.");
            }
            emailClienteField.textProperty().addListener((observable, oldValue, newValue) -> {
                filtraOrdiniPerEmail();
            });
        });

        colIdOrdine.setCellValueFactory(cellData -> cellData.getValue().idProgrammazioneProperty().asObject());
        colDataSpedizione.setCellValueFactory(cellData -> cellData.getValue().proxConsegnaProperty());
        colFrequenza.setCellValueFactory(cellData -> cellData.getValue().frequenzaProperty());
        colClienteEmail.setCellValueFactory(cellData -> cellData.getValue().clienteEmailProperty());
        colOrario.setCellValueFactory(cellData -> cellData.getValue().orarioProperty());
        colDataFine.setCellValueFactory(cellData -> cellData.getValue().dataFineProperty());

        tableOrdini.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        caricaOrdiniProgrammati();
    }

    private void caricaOrdiniProgrammati() {
        listaOrdini.clear();
        try {
            listaOrdini.addAll(programmazioneDAO.getAllOrdiniProgrammati());
            tableOrdini.setItems(listaOrdini);
        } catch (SQLException e) {
            mostraErrore("Errore nel caricamento degli ordini programmati.");
        }
    }

    @FXML
    public void filtraOrdiniPerEmail() {
        String email = emailClienteField.getText().trim();

        listaOrdini.clear();

        try {
            if (email.isEmpty()) {
                listaOrdini.addAll(programmazioneDAO.getAllOrdiniProgrammati());
            } else {
                listaOrdini.addAll(programmazioneDAO.getOrdiniProgrammatiPerEmail("%" + email + "%"));
            }

            tableOrdini.setItems(listaOrdini);

        } catch (SQLException e) {
            mostraErrore("Errore nel filtro degli ordini per email.");
        }
    }

    @FXML
    private void modificaOrdine(ActionEvent event) {
        Programmazione ordineSelezionato = tableOrdini.getSelectionModel().getSelectedItem();

        if (ordineSelezionato == null) {
            mostraErrore("Seleziona un ordine da modificare.");
            return;
        }

        Dialog<Programmazione> dialog = new Dialog<>();
        dialog.setTitle("Modifica Ordine");
        dialog.setHeaderText("Modifica i dettagli dell'ordine selezionato.");

        dialog.getDialogPane().getStyleClass().add("custom-dialog");
        dialog.getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        ButtonType modificaButtonType = new ButtonType("Modifica", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(modificaButtonType, ButtonType.CANCEL);

        dialog.getDialogPane().lookupButton(ButtonType.CANCEL).getStyleClass().add("cancelButton");

        ComboBox<String> frequenzaComboBox = new ComboBox<>();
        frequenzaComboBox.getItems().addAll("settimanale", "mensile", "trimestrale");
        frequenzaComboBox.setValue(ordineSelezionato.getFrequenza());

        ComboBox<String> orarioComboBox = new ComboBox<>();
        orarioComboBox.getItems().addAll("mattina", "pomeriggio");
        orarioComboBox.setValue(ordineSelezionato.getOrario());

        TextField emailClienteField = new TextField(ordineSelezionato.getClienteEmail());

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(
                new Label("Frequenza:"), frequenzaComboBox,
                new Label("Orario:"), orarioComboBox,
                new Label("Email Cliente:"), emailClienteField
        );
        dialog.getDialogPane().setContent(vbox);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == modificaButtonType) {
                ordineSelezionato.setFrequenza(frequenzaComboBox.getValue());
                ordineSelezionato.setOrario(orarioComboBox.getValue());
                ordineSelezionato.setClienteEmail(emailClienteField.getText());

                try {
                    programmazioneDAO.aggiornaOrdineProgrammato(ordineSelezionato);
                    caricaOrdiniProgrammati();
                } catch (SQLException e) {
                    mostraErrore("Errore durante l'aggiornamento dell'ordine.");
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    @FXML
    private void eliminaOrdine(ActionEvent event) {
        Programmazione ordineSelezionato = tableOrdini.getSelectionModel().getSelectedItem();

        if (ordineSelezionato == null) {
            mostraErrore("Seleziona un ordine da eliminare.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma Cancellazione");
        alert.setHeaderText("Sei sicuro di voler cancellare questa programmazione?");
        alert.setContentText("L'operazione non può essere annullata.");

        ButtonType buttonTypeYes = new ButtonType("Sì");
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == buttonTypeYes) {
            try {
                programmazioneDAO.eliminaOrdineProgrammato(ordineSelezionato.getIdProgrammazione());
                caricaOrdiniProgrammati();
            } catch (SQLException e) {
                mostraErrore("Errore durante l'eliminazione dell'ordine.");
            }
        }
    }

    @FXML
    private void apriCreaProgrammazione(ActionEvent event) {
        try {
            URL fxmlLocation = getClass().getResource("/com/uninadelivery/view/createProgram.fxml");
            if (fxmlLocation == null) {
                throw new IOException("FXML file not found!");
            }
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            AnchorPane root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Crea Programmazione");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostraErrore("Errore nell'aprire la finestra di creazione programmazione.");
        }
    }

    private void mostraErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

}