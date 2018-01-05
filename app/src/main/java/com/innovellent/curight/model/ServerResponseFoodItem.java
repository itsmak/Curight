package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mak on 1/5/2018.
 */

public class ServerResponseFoodItem {

    @SerializedName("Code")
    private Long Code;
    @SerializedName("Results")
    private ArrayList<FoodItem_Feed> Results=new ArrayList<>();

    public Long getCode() {
        return Code;
    }

    public void setCode(Long code) {
        Code = code;
    }

    public ArrayList<FoodItem_Feed> getResults() {
        return Results;
    }

    public void setResults(ArrayList<FoodItem_Feed> results) {
        Results = results;
    }
}
