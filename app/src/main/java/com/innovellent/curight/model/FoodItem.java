package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FoodItem {

    @SerializedName("foodName")
    private String foodName;
    @SerializedName("foodUnits")
    private ArrayList<FoodUnit> units;

    public FoodItem(String foodName, ArrayList<FoodUnit> units) {
        this.foodName = foodName;
        this.units = units;
    }

    public String getName() {
        return foodName;
    }

    public void setName(String foodName) {
        this.foodName = foodName;
    }

    public ArrayList<FoodUnit> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<FoodUnit> foodUnits) {
        this.units = foodUnits;
    }
}
