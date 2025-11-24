package com.example.papaspizzeria.sweproject.models;

public class LoginRequest {
    public String phonenumber;
    public String password;

    public LoginRequest() {}

    public LoginRequest(String phonenumber, String password) {
        this.phonenumber = phonenumber;
        this.password = password;
    }
}