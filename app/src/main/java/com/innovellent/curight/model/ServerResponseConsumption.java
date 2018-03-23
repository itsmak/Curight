package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mak on 3/23/2018.
 */

public class ServerResponseConsumption {


    @SerializedName("Code")
    private int Code;
    @SerializedName("Results")
    private String Results;

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getResults() {
        return Results;
    }

    public void setResults(String results) {
        Results = results;
    }


}
