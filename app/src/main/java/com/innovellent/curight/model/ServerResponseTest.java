package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class ServerResponseTest {
    @SerializedName("Code")
    private int Code;
    @SerializedName("Results")
    private ArrayList<Test> Results=new ArrayList<>();

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public ArrayList<Test> getResults() {
        return Results;
    }

    public void setResults(ArrayList<Test> results) {
        this.Results = results;
    }
}
