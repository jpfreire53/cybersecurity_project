package com.example.cybersecurity_project.Util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public class CryptoUtil {
    public static String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        Key key = new SecretKeySpec("12345678".getBytes(), "DES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }
}
