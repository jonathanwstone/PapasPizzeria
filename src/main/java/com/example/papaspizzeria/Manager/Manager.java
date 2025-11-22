package com.example.papaspizzeria.Manager;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Manager")
public class Manager {

    @Id
    @Column(name = "manager_id", unique = true)
    private String managerId;

    private String name;
    private String address;

    public Manager() {
    }

    public Manager(String managerId, String name, String address) {
        this.managerId = managerId;
        this.name = name;
        this.address = address;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
