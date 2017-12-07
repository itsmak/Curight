package com.innovellent.curight.model;

import java.util.ArrayList;


public class ServerResponseTest {
    private String Code;
    private ArrayList<Test> Results=new ArrayList<>();

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public ArrayList<Test> getResults() {
        return Results;
    }

    public void setResults(ArrayList<Test> results) {
        this.Results = results;
    }
}
