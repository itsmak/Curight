package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class ServerResponseOffer {

    @SerializedName("Code")
    private int code;

    @SerializedName("Results")
    private ArrayList<Article_FEED> results=new ArrayList<>();

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<Article_FEED> getResults() {
        return results;
    }

    public void setResults(ArrayList<Article_FEED> results) {
        this.results = results;
    }
}
