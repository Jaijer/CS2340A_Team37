package com.example.cs2340_project.model;

public class Ingredient extends Meal {
    private String name;
    private int quantity;
    private int calorie;

    public Ingredient(String name, Integer calorieCount, Integer quantity) {
        super(name, calorieCount);
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }
}
