package com.uninadelivery.model.entities;

import java.time.LocalDate;

public class Ordine {
    private int idOrdine;
    private LocalDate dataOrdine;
    private boolean completamento;
    private String emailCliente;
    private int numeroProdotti;

    public Ordine(int idOrdine, LocalDate dataOrdine, Boolean completamento, String emailCliente, int numeroProdotti) {
        this.idOrdine = idOrdine;
        this.dataOrdine = dataOrdine;
        this.completamento = completamento;
        this.emailCliente = emailCliente;
        this.numeroProdotti = numeroProdotti;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
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

    public int getNumeroProdotti() {
        return numeroProdotti;
    }

    public void setNumeroProdotti(int numeroProdotti) {
        this.numeroProdotti = numeroProdotti;
    }

    @Override
    public String toString() {
        return "Ordine{" +
                "idOrdine=" + idOrdine +
                ", dataOrdine=" + dataOrdine +
                ", completamento=" + completamento +
                ", emailCliente='" + emailCliente + '\'' +
                ", numeroProdotti=" + numeroProdotti +
                '}';
    }
}