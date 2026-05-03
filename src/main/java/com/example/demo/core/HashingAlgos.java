package com.example.demo.core;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashingAlgos {
    public static byte[] MD5(byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md.digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
