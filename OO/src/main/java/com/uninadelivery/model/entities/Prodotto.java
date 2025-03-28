package com.uninadelivery.model.entities;

public class Prodotto {
    private int idProdotto;
    private String nomeProdotto;
    private String dimensioni;
    private double peso;
    private int quantitaDisp;
    private double prezzo;
    private int magazzino;

    public Prodotto(int idProdotto, String nomeProdotto, String dimensioni, double peso, int quantitaDisp, double prezzo, int magazzino) {
        this.idProdotto = idProdotto;
        this.nomeProdotto = nomeProdotto;
        this.dimensioni = dimensioni;
        this.peso = peso;
        this.quantitaDisp = quantitaDisp;
        this.prezzo = prezzo;
        this.magazzino = magazzino;
    }

    public int getIdProdotto() {
        return idProdotto;
    }


    public String getNomeProdotto() {
        return nomeProdotto;
    }


    public String getDimensioni() {
        return dimensioni;
    }


    public double getPeso() {
        return peso;
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

    public int getMagazzino() {
        return magazzino;
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
