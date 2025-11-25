package com.example.papaspizzeria;

import com.example.papaspizzeria.sweproject.models.Order;
import com.example.papaspizzeria.sweproject.models.OrderItem;
import com.example.papaspizzeria.sweproject.models.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class to store order information across different screens
 * This tracks the current order being built by the user
 */
public class OrderCart {
    private static OrderCart instance;

    private Order currentOrder;
    private List<OrderItem> orderItems;

    // Order selections - these store what the user picked
    private String selectedCrust;
    private String selectedSize;
    private String selectedSauce;
    private List<String> selectedToppings;
    private String selectedBeverage;
    private String selectedDessert;
    private String selectedSide;

    // Store the actual menu items selected
    private MenuItem crustMenuItem;
    private MenuItem sizeMenuItem;
    private MenuItem sauceMenuItem;
    private MenuItem beverageMenuItem;
    private MenuItem dessertMenuItem;
    private MenuItem sideMenuItem;

    private OrderCart() {
        reset();
    }

    public static OrderCart getInstance() {
        if (instance == null) {
            instance = new OrderCart();
        }
        return instance;
    }

    /**
     * Reset the cart to empty state
     */
    public void reset() {
        currentOrder = new Order();
        orderItems = new ArrayList<>();
        selectedCrust = null;
        selectedSize = null;
        selectedSauce = null;
        selectedToppings = new ArrayList<>();
        selectedBeverage = null;
        selectedDessert = null;
        selectedSide = null;
    }

    /**
     * Add an item to the order
     */
    public void addOrderItem(OrderItem item) {
        orderItems.add(item);
    }

    /**
     * Calculate total price of all items
     */
    public float calculateTotal() {
        float total = 0;
        for (OrderItem item : orderItems) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    /**
     * Get formatted order summary
     */
    public String getOrderSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Order Summary:\n\n");

        if (selectedCrust != null) {
            summary.append("Crust: ").append(selectedCrust).append("\n");
        }
        if (selectedSize != null) {
            summary.append("Size: ").append(selectedSize).append("\n");
        }
        if (selectedSauce != null) {
            summary.append("Sauce: ").append(selectedSauce).append("\n");
        }
        if (!selectedToppings.isEmpty()) {
            summary.append("Toppings: ").append(String.join(", ", selectedToppings)).append("\n");
        }
        if (selectedBeverage != null) {
            summary.append("Beverage: ").append(selectedBeverage).append("\n");
        }
        if (selectedDessert != null) {
            summary.append("Dessert: ").append(selectedDessert).append("\n");
        }
        if (selectedSide != null) {
            summary.append("Side: ").append(selectedSide).append("\n");
        }

        summary.append("\nTotal: $").append(String.format("%.2f", calculateTotal()));

        return summary.toString();
    }

    // Getters and Setters
    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getSelectedCrust() {
        return selectedCrust;
    }

    public void setSelectedCrust(String selectedCrust) {
        this.selectedCrust = selectedCrust;
    }

    public String getSelectedSize() {
        return selectedSize;
    }

    public void setSelectedSize(String selectedSize) {
        this.selectedSize = selectedSize;
    }

    public String getSelectedSauce() {
        return selectedSauce;
    }

    public void setSelectedSauce(String selectedSauce) {
        this.selectedSauce = selectedSauce;
    }

    public List<String> getSelectedToppings() {
        return selectedToppings;
    }

    public void addTopping(String topping) {
        selectedToppings.add(topping);
    }

    public void removeTopping(String topping) {
        selectedToppings.remove(topping);
    }

    public String getSelectedBeverage() {
        return selectedBeverage;
    }

    public void setSelectedBeverage(String selectedBeverage) {
        this.selectedBeverage = selectedBeverage;
    }

    public String getSelectedDessert() {
        return selectedDessert;
    }

    public void setSelectedDessert(String selectedDessert) {
        this.selectedDessert = selectedDessert;
    }

    public String getSelectedSide() {
        return selectedSide;
    }

    public void setSelectedSide(String selectedSide) {
        this.selectedSide = selectedSide;
    }

    // Menu item getters and setters
    public MenuItem getCrustMenuItem() {
        return crustMenuItem;
    }

    public void setCrustMenuItem(MenuItem crustMenuItem) {
        this.crustMenuItem = crustMenuItem;
    }

    public MenuItem getSizeMenuItem() {
        return sizeMenuItem;
    }

    public void setSizeMenuItem(MenuItem sizeMenuItem) {
        this.sizeMenuItem = sizeMenuItem;
    }

    public MenuItem getSauceMenuItem() {
        return sauceMenuItem;
    }

    public void setSauceMenuItem(MenuItem sauceMenuItem) {
        this.sauceMenuItem = sauceMenuItem;
    }

    public MenuItem getBeverageMenuItem() {
        return beverageMenuItem;
    }

    public void setBeverageMenuItem(MenuItem beverageMenuItem) {
        this.beverageMenuItem = beverageMenuItem;
    }

    public MenuItem getDessertMenuItem() {
        return dessertMenuItem;
    }

    public void setDessertMenuItem(MenuItem dessertMenuItem) {
        this.dessertMenuItem = dessertMenuItem;
    }

    public MenuItem getSideMenuItem() {
        return sideMenuItem;
    }

    public void setSideMenuItem(MenuItem sideMenuItem) {
        this.sideMenuItem = sideMenuItem;
    }
}