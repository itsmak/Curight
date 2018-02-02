package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mak on 1/31/2018.
 */

public class AddExerciseResponse
{
    @SerializedName("Code")
    private int code;

    @SerializedName("Results")
    private String Results;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResults() {
        return Results;
    }

    public void setResults(String results) {
        Results = results;
    }
}
