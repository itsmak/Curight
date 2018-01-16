package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mak on 1/3/2018.
 */

public class Category_Feed {

    @SerializedName("foodName")
    private String foodName;

    @SerializedName("foodUnits")
    private ArrayList<FoodUnit_Feed> foodUnits=new ArrayList<>();

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public ArrayList<FoodUnit_Feed> getFoodUnits() {
        return foodUnits;
    }

    public void setFoodUnits(ArrayList<FoodUnit_Feed> foodUnits) {
        this.foodUnits = foodUnits;
    }
}
