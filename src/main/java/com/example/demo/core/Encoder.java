package com.example.demo.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.charset.StandardCharsets;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Encoder {
    public static final int
            HEX = 1,
            BIN = 0,
            UTF8 = 2,
            ASCII = 3;

    private Object input; //pointer
    private int inputType, outputType;
    private byte[] medium; //normalized input

    public String oUTF8;
    public String oHEX;
    public String oASCII;
    public byte[] oBytes;

    public byte[] normalize() {
        switch (inputType) {
            case BIN -> medium = (byte[]) input;
            case HEX -> {
                if (!(input instanceof String)) {
                    throw new IllegalArgumentException("hex input expects String type");
                }
                String hex = input.toString();
                int len = hex.length();
                if (len % 2 != 0) {
                    throw new IllegalArgumentException("uneven string length");
                }

                medium = new byte[len / 2];
                for (int i = 0; i < len; i += 2) {
                    medium[i / 2] = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
                }
            }

            case UTF8 -> {
                if (!(input instanceof String)) {
                    throw new IllegalArgumentException("UTF-8 input expects String type");
                }
                medium = input.toString().getBytes(StandardCharsets.UTF_8);
            }
            case ASCII -> {
                if (!(input instanceof String)) {
                    throw new IllegalArgumentException("ASCII input expects String type");
                }
                medium = input.toString().getBytes(StandardCharsets.US_ASCII);
            }
            default -> throw new IllegalArgumentException("type unsupported");
        }
        return medium;
    }

    public void encode() {
        if (medium != null) {
            switch (outputType) {
                case BIN -> oBytes = medium;

                case HEX -> {
                    StringBuilder sb = new StringBuilder(medium.length * 2);
                    for (byte b : medium) {
                        sb.append(String.format("%02x", b));
                    }
                    oHEX = sb.toString();
                }
                case UTF8 -> oUTF8 = new String(medium, StandardCharsets.UTF_8);

                case ASCII -> oASCII = new String(medium, StandardCharsets.US_ASCII);

                default -> throw new IllegalArgumentException("type unsupported");
            }
        } else {
            throw new RuntimeException("null input in runtime");
        }
    }
}
