package com.uninadelivery.model.entities;

public class Prodotto {
    private int idProdotto;
    private String nomeProdotto;
    private String dimensioni;
    private double peso;
    private int quantitaDisp;
    private double prezzo;

    public Prodotto(int idProdotto, String nomeProdotto, String dimensioni, double peso, int quantitaDisp, double prezzo) {
        this.idProdotto = idProdotto;
        this.nomeProdotto = nomeProdotto;
        this.dimensioni = dimensioni;
        this.peso = peso;
        this.quantitaDisp = quantitaDisp;
        this.prezzo = prezzo;
    }

    public int getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }

    public String getNomeProdotto() {
        return nomeProdotto;
    }

    public void setNomeProdotto(String nomeProdotto) {
        this.nomeProdotto = nomeProdotto;
    }

    public String getDimensioni() {
        return dimensioni;
    }

    public void setDimensioni(String dimensioni) {
        this.dimensioni = dimensioni;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getQuantitaDisp() {
        return quantitaDisp;
    }

    public void setQuantitaDisp(int quantitaDisp) {
        this.quantitaDisp = quantitaDisp;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    @Override
    public String toString() {
        return "Prodotto{" +
                "idProdotto=" + idProdotto +
                ", nomeProdotto='" + nomeProdotto + '\'' +
                ", dimensioni='" + dimensioni + '\'' +
                ", peso=" + peso +
                ", quantitaDisp=" + quantitaDisp +
                ", prezzo=" + prezzo +
                '}';
    }
}
