package com.example.cs2340_project.model;


public class Recipe {
    private String name;
    private Integer totalCalories;
    private String ingredients;


    public Recipe() {
    }

    public Recipe(String name, String ingredients, Integer totalCalories) {
        this.name = name;
        this.ingredients = ingredients;
        this.totalCalories = totalCalories;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
    public String getName() {
        return name;
    }

    public Integer getTotalCalories() {
        return totalCalories;
    }
    public void setTotalCalories(Integer totalCalories) {
        this.totalCalories = totalCalories;
    }
}

