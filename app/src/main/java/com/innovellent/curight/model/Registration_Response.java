package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mak on 12/18/2017.
 */

public class Registration_Response {

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
