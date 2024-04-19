package com.example.cs2340_project.model;

public class Meal {
    protected String name;
    protected Integer calorieCount;
    public Meal(String name, Integer calorieCount) {
        this.name = name;
        this.calorieCount = calorieCount;
    }

    public String getName() {
        return name;
    }

    public Integer getCalorieCount() {
        return calorieCount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCalorieCount(Integer calorieCount) {
        this.calorieCount = calorieCount;
    }
}
