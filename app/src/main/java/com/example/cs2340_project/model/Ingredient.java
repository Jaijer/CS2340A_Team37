package com.example.cs2340_project.model;

public class Ingredient {
    private String name;
    private Integer quantity;
    private Integer calories;
    public Ingredient() {
        // It exists, woo!
    }

    public Ingredient(String name, Integer quantity, Integer calories) {
        this.name = name;
        this.calories = calories;
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

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }
}
