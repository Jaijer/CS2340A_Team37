package com.example.cs2340_project.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable {
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

    protected Recipe(Parcel in) {
        name = in.readString();
        ingredients = in.readString();
        totalCalories = in.readInt();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public Integer getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(Integer totalCalories) {
        this.totalCalories = totalCalories;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(ingredients);
        parcel.writeInt(totalCalories);
    }
}
