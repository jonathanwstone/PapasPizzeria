package com.example.papaspizzeria;

import com.example.papaspizzeria.sweproject.models.Customer;

public class Session {
    private static Session instance;

    private String phoneNumber;
    private String password;
    private Customer customer;

    Session() {}

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean isLoggedIn() {
        return phoneNumber != null && !phoneNumber.isBlank()
                && password != null && !password.isBlank()
                && customer != null;
    }

    public void clear() {
        phoneNumber = null;
        password = null;
        customer = null;
    }
}