package org.example.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {
    public static String hash(String password){
        // Create a MessageDigest object with the MD5 algorithm.
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");

            // Pass the data to be hashed to the MessageDigest object.
            md.update(password.getBytes());

            // Get the hash value.
            byte[] hash = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(b);
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
