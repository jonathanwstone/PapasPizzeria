package com.example.papaspizzeria.Customer;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public List<Customer> getCustomerByPhoneNumber(String number){
        return customerRepository.findAll().stream().filter(customer -> customer.getPhonenumber().toLowerCase().contains(number.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Customer addCustomer(Customer customer) {
        // IMPORTANT: can hash password
        // customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        Optional<Customer> existing = customerRepository.findByPhonenumber(customer.getPhonenumber());
        if (existing.isPresent()){
            throw new IllegalStateException("Phone number alredy registered");
        }

        customerRepository.save(customer);
        return customer;
    }


    public Customer updateCustomer(Customer update) {
        // Update by pk
        Optional<Customer> existingCustomer = customerRepository.findByPhonenumber(update.getPhonenumber());

        if (existingCustomer.isPresent()) {
            Customer customerToUpdate = existingCustomer.get();
            customerToUpdate.setAddress(update.getAddress());
            customerToUpdate.setCardnumber(update.getCardnumber());
            customerRepository.save(customerToUpdate);
            return customerToUpdate;
        }
        return null;
    }
    public Customer login(String phonenumber, String password){
        Optional<Customer> customerTemp=customerRepository.findByPhonenumber(phonenumber);
        if (customerTemp.isEmpty()){
            return null;
            // Not found with phonnumber = account doesnt exist
        }
        Customer cust=customerTemp.get();
        if (password.equals(cust.getPassword())){
            return cust;// Password matches
        }
        else{
            return null; // Incorrect password
        }
    }
    @Transactional
    public void deleteCustomer(String phonenumber) {

        customerRepository.deleteByPhonenumber(phonenumber);
    }
}