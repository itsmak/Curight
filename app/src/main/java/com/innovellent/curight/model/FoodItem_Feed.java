package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mak on 1/5/2018.
 */

public class FoodItem_Feed {

    @SerializedName("foodName")
    private String foodName;
    @SerializedName("foodUnits")
    private ArrayList<Food_Units> Results=new ArrayList<>();

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public ArrayList<Food_Units> getResults() {
        return Results;
    }

    public void setResults(ArrayList<Food_Units> results) {
        Results = results;
    }
}
