package com.uninadelivery.controller;

import com.uninadelivery.model.dao.ProgrammazioneDAO;
import com.uninadelivery.model.entities.Programmazione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.SQLException;
import java.time.LocalDate;

public class CreaProgrammazioneController {

    @FXML private TableView<Programmazione> tableProgrammazioni;
    @FXML private TableColumn<Programmazione, Integer> colId;
    @FXML private TableColumn<Programmazione, LocalDate> colProxConsegna;
    @FXML private TableColumn<Programmazione, LocalDate> colDataFine;
    @FXML private TableColumn<Programmazione, String> colOrario;
    @FXML private TableColumn<Programmazione, String> colFrequenza;
    @FXML private TableColumn<Programmazione, String> colCliente;

    @FXML private DatePicker dateProxConsegna;
    @FXML private DatePicker dateDataFine;
    @FXML private TextField txtOrario;
    @FXML private ComboBox<String> comboFrequenza;
    @FXML private TextField txtClienteEmail;
    @FXML private Button btnSalva;
    @FXML private Button btnElimina;

    private ProgrammazioneDAO programmazioneDAO = new ProgrammazioneDAO();
    private ObservableList<Programmazione> listaProgrammazioni = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(cellData -> cellData.getValue().idProgrammazioneProperty().asObject());
        colProxConsegna.setCellValueFactory(cellData -> cellData.getValue().proxConsegnaProperty());
        colDataFine.setCellValueFactory(cellData -> cellData.getValue().dataFineProperty());
        colOrario.setCellValueFactory(cellData -> cellData.getValue().orarioProperty());
        colFrequenza.setCellValueFactory(cellData -> cellData.getValue().frequenzaProperty());
        colCliente.setCellValueFactory(cellData -> cellData.getValue().clienteEmailProperty());

        comboFrequenza.setItems(FXCollections.observableArrayList("Settimanale", "Mensile", "Annuale"));
        caricaProgrammazioni();
    }

    private void caricaProgrammazioni() {
        try {
            listaProgrammazioni.setAll(programmazioneDAO.getAllOrdiniProgrammati());
            tableProgrammazioni.setItems(listaProgrammazioni);
        } catch (SQLException e) {
            mostraErrore("Errore nel caricamento delle programmazioni.");
        }
    }

    @FXML
    private void salvaProgrammazione(ActionEvent event) {
        if (dateProxConsegna.getValue() == null || dateDataFine.getValue() == null ||
                txtOrario.getText().isEmpty() || comboFrequenza.getValue() == null || txtClienteEmail.getText().isEmpty()) {
            mostraErrore("Compila tutti i campi prima di salvare.");
            return;
        }

        Programmazione nuovaProg = new Programmazione(
                0, // ID gestito dal DB
                dateProxConsegna.getValue(),
                dateDataFine.getValue(),
                txtOrario.getText(),
                comboFrequenza.getValue(),
                txtClienteEmail.getText()
        );

        programmazioneDAO.createProgrammazione(nuovaProg);
        caricaProgrammazioni();
        pulisciCampi();
    }

    @FXML
    private void eliminaProgrammazione(ActionEvent event) {
        Programmazione selezionata = tableProgrammazioni.getSelectionModel().getSelectedItem();
        if (selezionata == null) {
            mostraErrore("Seleziona una programmazione da eliminare.");
            return;
        }

        try {
            programmazioneDAO.eliminaOrdineProgrammato(selezionata.getIdProgrammazione());
            caricaProgrammazioni();
        } catch (SQLException e) {
            mostraErrore("Errore nell'eliminazione.");
        }
    }

    private void mostraErrore(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void pulisciCampi() {
        dateProxConsegna.setValue(null);
        dateDataFine.setValue(null);
        txtOrario.clear();
        comboFrequenza.getSelectionModel().clearSelection();
        txtClienteEmail.clear();
    }
}