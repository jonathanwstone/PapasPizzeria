package com.example.papaspizzeria.sweproject.models;

public class MenuItem {
    private int size_id;
    private float price;
    private String topping;
    private String name;

    public MenuItem() {}

    public MenuItem(int size_id, String name, float price, String topping) {
        this.size_id = size_id;
        this.name = name;
        this.price = price;
        this.topping = topping;
    }

    public int getSize_id() {
        return size_id;
    }

    public void setSize_id(int size_id) {
        this.size_id = size_id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getTopping() {
        return topping;
    }

    public void setTopping(String topping) {
        this.topping = topping;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "size_id=" + size_id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", topping='" + topping + '\'' +
                '}';
    }
}