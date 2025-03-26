package com.uninadelivery.model.entities;

import java.time.LocalDate;

public class Spedizione {

    private int idSpedizione;
    private String destinazione;
    private LocalDate arrivoPrev;
    private String societa;
    private String stato;
    private LocalDate dataSped;
    private int idOrdine; // Foreign key
    private String corriere;
    private String mezzoTrasporto;

    // Modifica del costruttore per includere corriere e mezzo di trasporto
    public Spedizione(int idSpedizione, String destinazione, LocalDate arrivoPrev, String societa,
                      String stato, LocalDate dataSped, int idOrdine, String corriere, String mezzoTrasporto) {
        this.idSpedizione = idSpedizione;
        this.destinazione = destinazione;
        this.arrivoPrev = arrivoPrev;
        this.societa = societa;
        this.stato = stato;
        this.dataSped = dataSped;
        this.idOrdine = idOrdine;
        this.corriere = corriere;  // Nuovo campo
        this.mezzoTrasporto = mezzoTrasporto;  // Nuovo campo
    }

    // Getters
    public int getIdSpedizione() {
        return idSpedizione;
    }

    public String getDestinazione() {
        return destinazione;
    }

    public LocalDate getArrivoPrev() {
        return arrivoPrev;
    }

    public LocalDate getDataSped() {
        return dataSped;
    }

    public String getSocieta() {
        return societa;
    }

    public String getStato() {
        return stato;
    }

    public int getIdOrdine() {
        return idOrdine;
    }

    public String getCorriere() {
        return corriere;
    }

    public String getMezzoTrasporto() {
        return mezzoTrasporto;
    }

    // Setters
    public void setCorriere(String corriere) {
        this.corriere = corriere;
    }

    public void setMezzoTrasporto(String mezzoTrasporto) {
        this.mezzoTrasporto = mezzoTrasporto;
    }

    @Override
    public String toString() {
        return "Spedizione{" +
                "idSpedizione=" + idSpedizione +
                ", destinazione='" + destinazione + '\'' +
                ", arrivoPrev=" + arrivoPrev +
                ", societa='" + societa + '\'' +
                ", stato='" + stato + '\'' +
                ", dataSped=" + dataSped +
                ", idOrdine=" + idOrdine +
                ", corriere='" + corriere + '\'' +
                ", mezzoTrasporto='" + mezzoTrasporto + '\'' +
                '}';
    }
}
