package com.example.papaspizzeria.Customer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Customer")

// Customer Class
public class Customer {
    @Id
    @Column(name="phonenumber",unique = true)
    private String phonenumber;
    private String first_name;
    private String last_name;
    private String address;
    private String password;
    private String cardnumber;

    public Customer() {}
    public Customer(String id, String name, String first_name, String last_name, String address,String password, String cardnumber) {
        this.phonenumber = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address= address;
        this.password=password;
        this.cardnumber=cardnumber;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }


    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }



}