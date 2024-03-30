package com.example.cs2340_project.viewmodels;

import com.example.cs2340_project.model.Ingredient;
import java.util.List;

public interface SortingStrategies {

    List<Ingredient> sort(List<Ingredient> ingredients);

}
