package com.uninadelivery.model.entities;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Programmazione {

    private IntegerProperty idProgrammazione;
    private ObjectProperty<LocalDate> proxConsegna;
    private ObjectProperty<LocalDate> dataFine;
    private StringProperty orario;
    private StringProperty frequenza;
    private StringProperty clienteEmail;

    public Programmazione(int idProgrammazione, LocalDate proxConsegna, LocalDate dataFine, String orario, String frequenza, String clienteEmail) {
        this.idProgrammazione = new SimpleIntegerProperty(idProgrammazione);
        this.proxConsegna = new SimpleObjectProperty<>(proxConsegna);
        this.dataFine = new SimpleObjectProperty<>(dataFine);
        this.orario = new SimpleStringProperty(orario);
        this.frequenza = new SimpleStringProperty(frequenza);
        this.clienteEmail = new SimpleStringProperty(clienteEmail);
    }

    public IntegerProperty idProgrammazioneProperty() {
        return idProgrammazione;
    }

    public int getIdProgrammazione() {
        return idProgrammazione.get();
    }

    public ObjectProperty<LocalDate> proxConsegnaProperty() {
        return proxConsegna;
    }

    public LocalDate getProxConsegna() {
        return proxConsegna.get();
    }

    public ObjectProperty<LocalDate> dataFineProperty() {

        return dataFine;
    }

    public LocalDate getDataFine() {

        return dataFine.get();
    }

    public StringProperty orarioProperty() {
        return orario;
    }

    public String getOrario() {
        return orario.get();
    }

    public void setOrario(String orario) {
        this.orario.set(orario);
    }

    public StringProperty frequenzaProperty() {
        return frequenza;
    }

    public String getFrequenza() {
        return frequenza.get();
    }

    public void setFrequenza(String frequenza) {
        this.frequenza.set(frequenza);
    }

    public StringProperty clienteEmailProperty() {
        return clienteEmail;
    }

    public String getClienteEmail() {
        return clienteEmail.get();
    }

    public void setClienteEmail(String clienteEmail) {
        this.clienteEmail.set(clienteEmail);
    }

    @Override
    public String toString() {
        return "Programmazione{" +
                "idProgrammazione=" + idProgrammazione +
                ", proxConsegna=" + proxConsegna +
                ", dataFine=" + dataFine +
                ", orario='" + orario + '\'' +
                ", frequenza='" + frequenza + '\'' +
                ", clienteEmail='" + clienteEmail + '\'' +
                '}';
    }
}