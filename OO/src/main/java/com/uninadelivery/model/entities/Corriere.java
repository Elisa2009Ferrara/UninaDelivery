package com.uninadelivery.model.entities;

import java.util.Objects;

public class Corriere {
    private String nomeCorriere;
    private String cognomeCorriere;
    private String nTelefonoCorriere;
    private boolean disponibilita;

    public Corriere(String nomeCorriere, String cognomeCorriere, String nTelefonoCorriere, boolean disponibilita) {
        this.nomeCorriere = nomeCorriere;
        this.cognomeCorriere = cognomeCorriere;
        this.nTelefonoCorriere = nTelefonoCorriere;
        this.disponibilita = disponibilita;
    }

    public String getNomeCorriere() {
        return nomeCorriere;
    }

    public void setNomeCorriere(String nomeCorriere) {
        this.nomeCorriere = nomeCorriere;
    }

    public String getCognomeCorriere() {
        return cognomeCorriere;
    }

    public void setCognomeCorriere(String cognomeCorriere) {
        this.cognomeCorriere = cognomeCorriere;
    }

    public String getnTelefonoCorriere() {
        return nTelefonoCorriere;
    }

    public void setnTelefonoCorriere(String nTelefonoCorriere) {
        this.nTelefonoCorriere = nTelefonoCorriere;
    }

    public Boolean getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(boolean disponibilita) {
        this.disponibilita = disponibilita;
    }

    @Override
    public String toString() {
        return nomeCorriere + " " + cognomeCorriere;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Corriere corriere = (Corriere) o;
        return disponibilita == corriere.disponibilita &&
                Objects.equals(nomeCorriere, corriere.nomeCorriere) &&
                Objects.equals(cognomeCorriere, corriere.cognomeCorriere) &&
                Objects.equals(nTelefonoCorriere, corriere.nTelefonoCorriere);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomeCorriere, cognomeCorriere, nTelefonoCorriere, disponibilita);
    }
}
