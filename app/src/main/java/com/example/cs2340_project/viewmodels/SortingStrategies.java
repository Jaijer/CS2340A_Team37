package com.example.cs2340_project.viewmodels;

import com.example.cs2340_project.model.Ingredient;
import com.example.cs2340_project.model.Recipe;

import java.util.List;

public interface SortingStrategies {

    List<Ingredient> sort(List<Ingredient> ingredients);
    List<Recipe> sortRecipe(List<Recipe> ingredients);

}
