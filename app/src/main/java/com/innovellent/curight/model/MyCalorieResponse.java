package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mak on 1/31/2018.
 */

public class MyCalorieResponse {
    @SerializedName("Code")
    private int code;

   @SerializedName("Results")
    private  Result_CAl Results;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Result_CAl getResults() {
        return Results;
    }

    public void setResults(Result_CAl results) {
        Results = results;
    }
}
