package com.example.papaspizzeria.Employee;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    public Employee addEmployee(Employee employee) {
        Optional<Employee> existing = employeeRepository.findByEmployeeId(employee.getEmployeeId());
        if (existing.isPresent()) {
            throw new IllegalStateException("Employee ID already exists");
        }
        employeeRepository.save(employee);
        return employee;
    }

    public Employee updateEmployee(Employee updated) {
        Optional<Employee> existing = employeeRepository.findByEmployeeId(updated.getEmployeeId());

        if (existing.isPresent()) {
            Employee emp = existing.get();
            emp.setName(updated.getName());
            emp.setAddress(updated.getAddress());
            emp.setOrdersCompleted(updated.getOrdersCompleted());
            employeeRepository.save(emp);
            return emp;
        }

        return null;
    }

    @Transactional
    public void deleteEmployee(String employeeId) {
        employeeRepository.deleteByEmployeeId(employeeId);
    }
}

