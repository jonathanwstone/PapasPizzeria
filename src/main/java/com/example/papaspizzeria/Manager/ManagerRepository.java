package com.example.papaspizzeria.Manager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, String> {

    void deleteByManagerId(String managerId);

    Optional<Manager> findByManagerId(String managerId);
}
