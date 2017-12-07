package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mak on 12/4/2017.
 */

public class MyServer_Response {

    @SerializedName("Code")
    private int code;

    @SerializedName("Results")
    ArrayList<JSON_FEED> results = new ArrayList<>();

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<JSON_FEED> getResults() {
        return results;
    }

    public void setResults(ArrayList<JSON_FEED> results) {
        this.results = results;
    }
}
