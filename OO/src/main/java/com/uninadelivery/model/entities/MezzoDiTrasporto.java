package com.uninadelivery.model.entities;

public class MezzoDiTrasporto {
    private String targa;
    private Integer capacitaPeso;
    private Integer capacitaSpazio;
    private Boolean disponibilita;
    private String modello;

    public MezzoDiTrasporto(String targa, Integer capacitaPeso, Integer capacitaSpazio, Boolean disponibilita, String modello) {
        this.setTarga(targa);
        this.setCapacitaPeso(capacitaPeso);
        this.setCapacitaSpazio(capacitaSpazio);
        this.setDisponibilita(disponibilita);
        this.setModello(modello);
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }

    public Integer getCapacitaPeso() {
        return capacitaPeso;
    }

    public void setCapacitaPeso(Integer capacitaPeso) {
        this.capacitaPeso = capacitaPeso;
    }

    public Integer getCapacitaSpazio() {
        return capacitaSpazio;
    }

    public void setCapacitaSpazio(Integer capacitaSpazio) {
        this.capacitaSpazio = capacitaSpazio;
    }

    public Boolean getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(Boolean disponibilita) {
        this.disponibilita = disponibilita;
    }

    public String getModello() {
        return modello;
    }

    public void setModello(String modello) {
        this.modello = modello;
    }

    @Override
    public String toString() {
        return "MezzoDiTrasporto{" +
                "targa='" + targa + '\'' +
                ", capacitaPeso=" + capacitaPeso +
                ", capacitaSpazio=" + capacitaSpazio +
                ", disponibilita=" + disponibilita +
                ", modello='" + modello + '\'' +
                '}';
    }

}