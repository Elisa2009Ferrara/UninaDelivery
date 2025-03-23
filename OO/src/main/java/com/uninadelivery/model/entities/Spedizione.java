package com.uninadelivery.model.entities;

import java.time.LocalDate;


public class Spedizione {

    private int idSpedizione;
    private String destinazione;
    private LocalDate arrivoPrev;
    private String societa;
    private String stato;
    private LocalDate dataSped;

    public Spedizione(int idSpedizione, String destinazione, LocalDate arrivoPrev, String societa, String stato, LocalDate dataSped) {
        this.setIdSpedizione(idSpedizione);
        this.setDestinazione(destinazione);
        this.setArrivoPrev(arrivoPrev);
        this.setSocieta(societa);
        this.setStato(stato);
        this.setDataSped(dataSped);
    }

    public int getIdSpedizione() {
        return idSpedizione;
    }

    public void setIdSpedizione(int idSpedizione) {
        this.idSpedizione = idSpedizione;
    }

    public String getDestinazione() {
        return destinazione;
    }

    public void setDestinazione(String destinazione) {
        this.destinazione = destinazione;
    }

    public LocalDate getArrivoPrev() {
        return arrivoPrev;
    }

    public void setArrivoPrev(LocalDate arrivoPrev) {
        this.arrivoPrev = arrivoPrev;
    }

    public String getSocieta() {
        return societa;
    }

    public void setSocieta(String societa) {
        this.societa = societa;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public LocalDate getDataSped() {
        return dataSped;
    }

    public void setDataSped(LocalDate dataSped) {
        this.dataSped = dataSped;
    }

    @Override
    public String toString() {
        return "Spedizione{" + "idSpedizione=" + idSpedizione +
                ", destinazione=" + destinazione +
                ", arrivoPrev=" + arrivoPrev +
                ", societa=" + societa +
                ", stato=" + stato +
                ", dataSped=" + dataSped +
                '}';
    }
}
