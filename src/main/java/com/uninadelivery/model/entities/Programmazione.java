package com.uninadelivery.model.entities;

import java.sql.Date;
import java.time.LocalDate;

public class Programmazione {
    private int idProgrammazione;
    private LocalDate proxConsegna;
    private LocalDate dataFine;
    private String orario;
    private String frequenza;

    public Programmazione(int idProgrammazione, LocalDate proxConsegna, LocalDate dataFine, String orario, String frequenza) {
        this.setIdProgrammazione(idProgrammazione);
        this.setProxConsegna(proxConsegna);
        this.setDataFine(dataFine);
        this.setOrario(orario);
        this.setFrequenza(frequenza);
    }

    public int getIdProgrammazione() {
        return idProgrammazione;
    }

    public void setIdProgrammazione(int idProgrammazione) {
        this.idProgrammazione = idProgrammazione;
    }

    public LocalDate getProxConsegna() {
        return proxConsegna;
    }

    public void setProxConsegna(LocalDate proxConsegna) {
        this.proxConsegna = proxConsegna;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    public String getOrario() {
        return orario;
    }

    public void setOrario(String orario) {
        this.orario = orario;
    }

    public String getFrequenza() {
        return frequenza;
    }

    public void setFrequenza(String frequenza) {
        this.frequenza = frequenza;
    }

    @Override
    public String toString() {
        return "Programmazione{" +
                "idProgrammazione=" + idProgrammazione +
                ", proxConsegna=" + proxConsegna +
                ", dataFine=" + dataFine +
                ", orario='" + orario + '\'' +
                ", frequenza='" + frequenza + '\'' +
                '}';
    }
}
