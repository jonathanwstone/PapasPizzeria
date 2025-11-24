package com.example.papaspizzeria.sweproject.api;

import com.example.papaspizzeria.sweproject.models.MenuItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MenuItemService {

    /**
     * Get all menu items
     */
    public static List<MenuItem> getAllMenuItems() throws Exception {
        String response = ApiClient.get("/menu", String.class);
        Gson gson = new Gson();
        Type listType = new TypeToken<List<MenuItem>>(){}.getType();
        return gson.fromJson(response, listType);
    }

    /**
     * Get menu item by size_id
     */
    public static MenuItem getMenuItemById(int sizeId) throws Exception {
        String response = ApiClient.get("/menu?size_id=" + sizeId, String.class);
        Gson gson = new Gson();
        Type listType = new TypeToken<List<MenuItem>>(){}.getType();
        List<MenuItem> items = gson.fromJson(response, listType);

        if (items != null && !items.isEmpty()) {
            return items.get(0);
        }
        return null;
    }

    /**
     * Add a new menu item
     */
    public static MenuItem addMenuItem(MenuItem item) throws Exception {
        return ApiClient.post("/menu", item, MenuItem.class);
    }

    /**
     * Update an existing menu item
     */
    public static MenuItem updateMenuItem(MenuItem item) throws Exception {
        return ApiClient.put("/menu", item, MenuItem.class);
    }

    /**
     * Delete a menu item
     */
    public static void deleteMenuItem(int sizeId) throws Exception {
        ApiClient.delete("/menu/" + sizeId);
    }
}