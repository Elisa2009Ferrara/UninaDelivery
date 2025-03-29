package com.uninadelivery.model.entities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.logging.Logger;

public class Operatore {
    private static final Logger LOGGER = Logger.getLogger(Operatore.class.getName());

    private String emailOperatore;
    private String password;
    private String nomeOperatore;
    private String cognomeOperatore;
    private String nTelefonoOperatore;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public Operatore(String emailOperatore, String password, String nomeOperatore, String cognomeOperatore, String nTelefonoOperatore) {
        this.setEmailOperatore(emailOperatore);
        this.setPassword(password);
        this.setNomeOperatore(nomeOperatore);
        this.setCognomeOperatore(cognomeOperatore);
        this.setnTelefonoOperatore(nTelefonoOperatore);
    }

    public String getEmailOperatore() {
        return emailOperatore;
    }

    public void setEmailOperatore(String emailOperatore) {
        Matcher matcher = EMAIL_PATTERN.matcher(emailOperatore);
        if (matcher.matches()) {
            this.emailOperatore = emailOperatore;
        } else {
            LOGGER.warning("Formato email non valido: " + emailOperatore);
            throw new IllegalArgumentException("Formato email non valido.");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.length() >= 6) {
            this.password = password;
        } else {
            LOGGER.warning("Tentativo di impostare una password troppo corta.");
            throw new IllegalArgumentException("La password deve essere lunga almeno 6 caratteri.");
        }
    }

    public String getNomeOperatore() {
        return nomeOperatore;
    }

    public void setNomeOperatore(String nomeOperatore) {
        this.nomeOperatore = nomeOperatore;
    }

    public String getCognomeOperatore() {
        return cognomeOperatore;
    }

    public void setCognomeOperatore(String cognomeOperatore) {
        this.cognomeOperatore = cognomeOperatore;
    }

    public String getnTelefonoOperatore() {
        return nTelefonoOperatore;
    }

    public void setnTelefonoOperatore(String nTelefonoOperatore) {
        if (nTelefonoOperatore.matches("\\d{10}")) {
            this.nTelefonoOperatore = nTelefonoOperatore;
        } else {
            LOGGER.warning("Numero di telefono non valido: " + nTelefonoOperatore);
            throw new IllegalArgumentException("Il numero di telefono deve avere 10 cifre.");
        }
    }

}