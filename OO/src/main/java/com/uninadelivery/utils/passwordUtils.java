package com.uninadelivery.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class passwordUtils {

    // Funzione per generare hash MD5
    public static String generateMD5(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashBytes = md.digest(password.getBytes());

        // Converti il risultato in una stringa esadecimale
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}