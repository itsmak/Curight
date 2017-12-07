package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Pramod on 16-Nov-2017.
 */

public class FoodDetail {
    @SerializedName("foodName")
    private String foodName;
    private ArrayList<FoodUnit> foodUnits;

    public FoodDetail(String foodName, ArrayList<FoodUnit> foodUnits) {
        this.foodName = foodName;
        this.foodUnits = foodUnits;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public ArrayList<FoodUnit> getFoodUnits() {
        return foodUnits;
    }

    public void setFoodUnits(ArrayList<FoodUnit> foodUnits) {
        this.foodUnits = foodUnits;
    }
}
