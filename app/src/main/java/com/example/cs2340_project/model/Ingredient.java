package com.example.cs2340_project.model;

public class Ingredient extends Meal {
    private String name;
    private Integer quantity;
    private Integer calorie;

    public Ingredient(String name, Integer calorieCount, Integer quantity) {
        super(name, calorieCount);
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
