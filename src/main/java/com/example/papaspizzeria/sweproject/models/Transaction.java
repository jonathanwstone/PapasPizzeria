package com.example.papaspizzeria.sweproject.models;

import java.time.LocalDateTime;

public class Transaction {
    private int id;
    private int orderId;
    private float amount;
    private String method;
    private LocalDateTime timestamp;

    public Transaction() {}

    public Transaction(float amount, String method) {
        this.amount = amount;
        this.method = method;
        this.timestamp = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}