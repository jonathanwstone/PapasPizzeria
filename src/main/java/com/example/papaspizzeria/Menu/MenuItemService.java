package com.example.papaspizzeria.Menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemService {

    private final MenuItemRepository repository;

    @Autowired
    public MenuItemService(MenuItemRepository repository) {
        this.repository = repository;
    }

    public List<MenuItem> getMenuItems() {
        return repository.findAll();
    }

    public MenuItem addMenuItem(MenuItem item) {
        return repository.save(item);
    }

    public MenuItem updateMenuItem(MenuItem item) {
        if (repository.existsById(item.getSize_id())) {
            return repository.save(item);
        }
        return null;
    }

    public void deleteMenuItem(int size_id) {
        repository.deleteById(size_id);
    }
}
