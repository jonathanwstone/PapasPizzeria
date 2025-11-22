package com.example.papaspizzeria.Manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/Manager")
public class ManagerController {

    private final ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping
    public List<Manager> getManagers(@RequestParam(required = false) String managerId) {
        if (managerId != null) {
            return managerService.getManagers().stream()
                    .filter(m -> m.getManagerId().equalsIgnoreCase(managerId))
                    .toList();
        }
        return managerService.getManagers();
    }

    @PostMapping
    public ResponseEntity<Manager> addManager(@RequestBody Manager manager) {
        Manager created = managerService.addManager(manager);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Manager> updateManager(@RequestBody Manager manager) {
        Manager updated = managerService.updateManager(manager);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{managerId}")
    public ResponseEntity<String> deleteManager(@PathVariable String managerId) {
        managerService.deleteManager(managerId);
        return new ResponseEntity<>("Manager deleted successfully", HttpStatus.OK);
    }
}

