package com.example.papaspizzeria.Manager;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ManagerService {

    private final ManagerRepository managerRepository;

    @Autowired
    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    public List<Manager> getManagers() {
        return managerRepository.findAll();
    }

    public Manager addManager(Manager manager) {
        Optional<Manager> existing = managerRepository.findByManagerId(manager.getManagerId());
        if (existing.isPresent()) {
            throw new IllegalStateException("Manager ID already exists");
        }
        managerRepository.save(manager);
        return manager;
    }

    public Manager updateManager(Manager updated) {
        Optional<Manager> existing = managerRepository.findByManagerId(updated.getManagerId());

        if (existing.isPresent()) {
            Manager mgr = existing.get();
            mgr.setName(updated.getName());
            mgr.setAddress(updated.getAddress());
            managerRepository.save(mgr);
            return mgr;
        }

        return null;
    }

    @Transactional
    public void deleteManager(String managerId) {
        managerRepository.deleteByManagerId(managerId);
    }
}

