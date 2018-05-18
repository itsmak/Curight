package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mak on 5/17/2018.
 */

public class ServerResponseCategory {

    @SerializedName("Code")
    private Integer Code;

    @SerializedName("Results")
    private ArrayList<CategoryResult> Results=new ArrayList<>();

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer code) {
        Code = code;
    }

    public ArrayList<CategoryResult> getResults() {
        return Results;
    }

    public void setResults(ArrayList<CategoryResult> results) {
        Results = results;
    }
}
