package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mak on 3/6/2018.
 */

public class ServerResponse_getCalories {

    @SerializedName("Code")
    private String Code;
    @SerializedName("Results")
    private ArrayList<CalorieList> Results=new ArrayList<>();

    public ServerResponse_getCalories(String code, ArrayList<CalorieList> results) {
        Code = code;
        Results = results;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public ArrayList<CalorieList> getResults() {
        return Results;
    }

    public void setResults(ArrayList<CalorieList> results) {
        Results = results;
    }
}
