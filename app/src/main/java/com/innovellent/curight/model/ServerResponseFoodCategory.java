package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mak on 1/3/2018.
 */

public class ServerResponseFoodCategory {

    @SerializedName("Code")
    private Long Code;
    @SerializedName("Results")
    private ArrayList<Category_Feed> Results=new ArrayList<>();

    public Long getCode() {
        return Code;
    }

    public void setCode(Long code) {
        Code = code;
    }

    public ArrayList<Category_Feed> getResults() {
        return Results;
    }

    public void setResults(ArrayList<Category_Feed> results) {
        Results = results;
    }
}
