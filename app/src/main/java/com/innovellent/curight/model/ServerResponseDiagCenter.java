package com.innovellent.curight.model;

import java.util.ArrayList;


public class ServerResponseDiagCenter {
    private String Code;
    private ArrayList<Center> Results=new ArrayList<>();

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public ArrayList<Center> getResults() {
        return Results;
    }

    public void setResults(ArrayList<Center> results) {
        this.Results = results;
    }
}
