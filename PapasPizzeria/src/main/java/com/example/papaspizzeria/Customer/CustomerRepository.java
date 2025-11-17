package com.example.papaspizzeria.Customer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,String>{
    //methods for updating/deleting from database
    //void deleteByFirstName(String firstName);
    void deleteByPhonenumber(String phonenumber);


    //Optional<Customer> findByFirstName(String firstName);
    Optional<Customer> findByPhonenumber(String phonenumber);
}