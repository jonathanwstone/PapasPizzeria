package com.example.papaspizzeria.Employee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Employee")
public class Employee {

    @Id
    @Column(name = "employee_id", unique = true)
    private String employeeId;

    private String name;
    private String address;
    private int ordersCompleted;

    public Employee() {
    }

    public Employee(String employeeId, String name, String address, int ordersCompleted) {
        this.employeeId = employeeId;
        this.name = name;
        this.address = address;
        this.ordersCompleted = ordersCompleted;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
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

    public int getOrdersCompleted() {
        return ordersCompleted;
    }

    public void setOrdersCompleted(int ordersCompleted) {
        this.ordersCompleted = ordersCompleted;
    }
}