package com.uninadelivery.model.entities;

public class DettaglioOrdine {
    private int idDettaglio;
    private int quantita;

    // Costruttore
    public DettaglioOrdine(int idDettaglio, int quantita) {
        this.idDettaglio = idDettaglio;
        this.quantita = quantita;
    }

    // Getter e Setter
    public int getIdDettaglio() {
        return idDettaglio;
    }

    public void setIdDettaglio(int idDettaglio) {
        this.idDettaglio = idDettaglio;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    // Metodo toString() per debug
    @Override
    public String toString() {
        return "DettaglioOrdine{" +
                "idDettaglio=" + idDettaglio +
                ", quantita=" + quantita +
                '}';
    }
}

