package br.edu.uag.aruagi.control.util.cript;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 */
public class SHA256 {

    public static String encode(String value) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte messageDigest[] = algorithm.digest(value.getBytes("UTF-8"));

        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            hexString.append(String.format("%02X", 0xFF & b));
        }
        return hexString.toString();
    }
    
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        System.out.println(SHA256.encode("15091992"));
    }
}
