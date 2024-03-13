package com.example.cs2340_project.model;

public class Meal {
    private final String name;
    private final int calorieCount;

    public Meal(String name, int calorieCount) {
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
