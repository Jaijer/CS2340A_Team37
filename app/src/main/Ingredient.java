package com.example.cs2340_project.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {
    private String name;
    private Integer quantity;
    private Integer calories;
    public Ingredient() {
        // The code will break if you remove this.
    }

    public Ingredient(String name, Integer quantity, Integer calories) {
        this.name = name;
        this.calories = calories;
        this.quantity = quantity;
    }

    protected Ingredient(Parcel in) {
        name = in.readString();
        quantity = (Integer) in.readValue(Integer.class.getClassLoader());
        calories = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public static final Parcelable.Creator<Ingredient> CREATOR
            = new Parcelable.Creator<Ingredient>() {
                @Override
        public Ingredient createFromParcel(Parcel in) {
                    return new Ingredient(in);
                }

                @Override
        public Ingredient[] newArray(int size) {
                    return new Ingredient[size];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeValue(quantity);
        dest.writeValue(calories);
    }
}
