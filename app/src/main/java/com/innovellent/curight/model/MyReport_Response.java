package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mak on 2/5/2018.
 */

public class MyReport_Response {

    @SerializedName("Results")
    ArrayList<Report_FEED> results = new ArrayList<>();
    @SerializedName("Code")
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<Report_FEED> getResults() {
        return results;
    }

    public void setResults(ArrayList<Report_FEED> results) {
        this.results = results;
    }
}
