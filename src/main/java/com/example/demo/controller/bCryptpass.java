package com.example.demo.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class bCryptpass {
    public static void main(String[] args) {
        String keyword;

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        keyword = encoder.encode("1");
        System.out.println(keyword);
    }
}
