package com.uninadelivery.model.entities;

import java.time.LocalDate;

public class Cliente {
    private String emailCliente;
    private String nTelefonoCliente;
    private String nome;
    private String cognome;
    private LocalDate dataNascita;
    private String indirizzoPredefinito;

    public Cliente(String emailCliente, String nTelefonoCliente, String nome, String cognome, LocalDate dataNascita, String indirizzoPredefinito) {
        this.emailCliente = emailCliente;
        this.nTelefonoCliente = nTelefonoCliente;
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.indirizzoPredefinito = indirizzoPredefinito;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public String getnTelefonoCliente() {
        return nTelefonoCliente;
    }

    public void setnTelefonoCliente(String nTelefonoCliente) {
        this.nTelefonoCliente = nTelefonoCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getIndirizzoPredefinito() {
        return indirizzoPredefinito;
    }

    public void setIndirizzoPredefinito(String indirizzoPredefinito) {
        this.indirizzoPredefinito = indirizzoPredefinito;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "emailCliente='" + emailCliente + '\'' +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", dataNascita=" + dataNascita +
                ", indirizzoPredefinito='" + indirizzoPredefinito + '\'' +
                '}';
    }
}