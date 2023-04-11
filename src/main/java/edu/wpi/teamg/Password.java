package edu.wpi.teamg;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
public class Password {
    String passwordToHash;
    String salt;
    static ArrayList<HashMap<Integer, String>> savedPasswords = new ArrayList<>();
    static HashMap<Integer, String> savedPassword = new HashMap<>();

    private static void set_SHA256_SecurePassword(String password, String salt) {
        int firstIndex = 0;
        int nextIndex = 1;
        savedPassword.put(firstIndex, password);
        savedPassword.put(nextIndex, salt);
        savedPasswords.add(savedPassword);
    }


    private static String get_SHA256_SecurePassword(int index) {
        String passwordToGet = savedPasswords.get(index).get(0);
        String saltToGet = savedPasswords.get(index).get(1);

        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(saltToGet.getBytes());
            byte[] bytes = md.digest(passwordToGet.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString(aByte, 16).substring(1)); //(aByte & 0xff)  + 0x100
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
