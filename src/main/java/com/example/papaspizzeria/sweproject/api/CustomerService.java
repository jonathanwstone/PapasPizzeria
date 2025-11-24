package com.example.papaspizzeria.sweproject.api;

import com.example.papaspizzeria.sweproject.models.Customer;
import com.example.papaspizzeria.sweproject.models.LoginRequest;

public class CustomerService {

    /**
     * Register a new customer
     */
    public static Customer register(Customer customer) throws Exception {
        return ApiClient.post("/Customer", customer, Customer.class);
    }

    /**
     * Login customer
     */
    public static Customer login(String phonenumber, String password) throws Exception {
        LoginRequest loginRequest = new LoginRequest(phonenumber, password);
        return ApiClient.post("/Customer/login", loginRequest, Customer.class);
    }

    /**
     * Get customer by phone number
     */
    public static Customer getCustomer(String phonenumber) throws Exception {
        return ApiClient.get("/Customer?phonenumber=" + phonenumber, Customer.class);
    }

    /**
     * Update customer information
     */
    public static Customer updateCustomer(Customer customer) throws Exception {
        return ApiClient.put("/Customer", customer, Customer.class);
    }

    /**
     * Delete customer account
     */
    public static void deleteCustomer(String phonenumber) throws Exception {
        ApiClient.delete("/Customer/" + phonenumber);
    }
}