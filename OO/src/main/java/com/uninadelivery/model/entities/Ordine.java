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

    public int getIdOrdine() {
        return idOrdine;
    }

    public LocalDate getDataOrdine() {
        return dataOrdine;
    }

    public int getNumeroProdotti() {
        return numeroProdotti;
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