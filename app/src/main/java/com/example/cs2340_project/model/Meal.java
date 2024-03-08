package com.example.cs2340_project.model;

public class Meal {
    private final String name;
    private final int calorieCount;

    public Meal(String name, int calorieCount) {
        // Checks that calories are not negative.
        if (calorieCount < 0) {
            throw new IllegalArgumentException("Calorie count cannot be negative");
        }
        // Null check and white space checks name
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
        this.calorieCount = calorieCount;
    }

    public String getName() {
        return name;
    }

    public int getCalorieCount() {
        return calorieCount;
    }
}
