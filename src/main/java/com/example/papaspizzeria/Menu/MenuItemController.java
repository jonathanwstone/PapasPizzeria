package com.example.papaspizzeria.Menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/menu")
public class MenuItemController {

    private final MenuItemService service;

    @Autowired
    public MenuItemController(MenuItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<MenuItem> getMenuItems(@RequestParam(required = false) Integer size_id) {
        if (size_id != null) {
            return service.getMenuItems().stream()
                    .filter(item -> item.getSize_id() == size_id)
                    .toList();
        }
        return service.getMenuItems();
    }

    @PostMapping
    public ResponseEntity<MenuItem> addMenuItem(@RequestBody MenuItem item) {
        MenuItem created = service.addMenuItem(item);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<MenuItem> updateMenuItem(@RequestBody MenuItem item) {
        MenuItem updated = service.updateMenuItem(item);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{size_id}")
    public ResponseEntity<String> deleteMenuItem(@PathVariable Integer size_id) {
        service.deleteMenuItem(size_id);
        return new ResponseEntity<>("Menu item deleted successfully", HttpStatus.OK);
    }
}
