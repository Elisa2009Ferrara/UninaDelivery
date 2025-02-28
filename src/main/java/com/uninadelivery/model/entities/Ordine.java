package com.uninadelivery.model.entities;

import java.time.LocalDate;

public class Ordine {
    private int idOrdine;
    private LocalDate dataOrdine;
    private boolean completamento;

    public Ordine(int idOrdine, LocalDate dataOrdine, Boolean completamento) {
        this.idOrdine = idOrdine;
        this.dataOrdine = dataOrdine;
        this.completamento = completamento;
    }

    // Costruttore di default
    public Ordine() {
        this.dataOrdine = LocalDate.now(); // Imposta la data corrente
        this.completamento = false; // Per default, un ordine non è completato
    }

    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public LocalDate getDataOrdine() {
        return dataOrdine;
    }

    public void setDataOrdine(LocalDate dataOrdine) {
        this.dataOrdine = dataOrdine;
    }

    public Boolean getCompletamento() {
        return completamento;
    }

    public void setCompletamento(boolean completamento) {
        this.completamento = completamento;
    }

    @Override
    public String toString() {
        return "Ordine{" +
                "idOrdine=" + idOrdine +
                ", dataOrdine=" + dataOrdine +
                ", completamento=" + completamento +
                '}';
    }
}