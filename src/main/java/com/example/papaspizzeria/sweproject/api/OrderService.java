package com.example.papaspizzeria.sweproject.api;

import com.example.papaspizzeria.sweproject.models.Order;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class OrderService {

    /**
     * Get all orders
     */
    public static List<Order> getAllOrders() throws Exception {
        String response = ApiClient.get("/orders", String.class);
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Order>>(){}.getType();
        return gson.fromJson(response, listType);
    }

    /**
     * Get order by ID
     */
    public static Order getOrderById(int orderId) throws Exception {
        return ApiClient.get("/orders/" + orderId, Order.class);
    }

    /**
     * Create a new order
     */
    public static Order createOrder(Order order) throws Exception {
        return ApiClient.post("/orders", order, Order.class);
    }

    /**
     * Update an existing order
     */
    public static Order updateOrder(Order order) throws Exception {
        return ApiClient.put("/orders", order, Order.class);
    }

    /**
     * Delete an order
     */
    public static void deleteOrder(int orderId) throws Exception {
        ApiClient.delete("/orders/" + orderId);
    }
}