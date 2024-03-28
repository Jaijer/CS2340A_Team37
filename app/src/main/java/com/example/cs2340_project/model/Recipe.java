package com.example.cs2340_project.model;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Integer> ingredientsQuantities;


    public Recipe(String name, ArrayList<Ingredient> ingredients, ArrayList<Integer> ingredientsQuantities) {
        this.name = name;
        this.ingredients = ingredients;
        this.ingredientsQuantities = ingredientsQuantities;
    }
}
