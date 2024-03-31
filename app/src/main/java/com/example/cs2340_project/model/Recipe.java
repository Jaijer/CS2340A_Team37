package com.example.cs2340_project.model;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private Integer totalCalories;
    private ArrayList<Ingredient> ingredients;


    public Recipe() {
    }

    public Recipe(String name, ArrayList<Ingredient> ingredients, Integer totalCalories) {
        this.name = name;
        this.ingredients = ingredients;
        this.totalCalories = totalCalories;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
    public String getName() {
        return name;
    }

    public Integer getTotalCalories() { return totalCalories; }
    public void setTotalCalories(Integer totalCalories) { this.totalCalories = totalCalories; }
}
