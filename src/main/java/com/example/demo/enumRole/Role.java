package com.example.demo.enumRole;

public enum Role {
    NONE(0),
    GUEST(1),
    EMPLOYEE(2),
    BUSINESS(4),
    ADMIN(8);

    private final int value;

     Role(int value) {
        this.value = value;
     }

    public int getValue(){
         return value;
    }

}
