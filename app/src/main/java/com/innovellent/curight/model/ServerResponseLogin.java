package com.innovellent.curight.model;

import java.util.ArrayList;


public class ServerResponseLogin {
    private String Code;
    private ArrayList<User> Results=new ArrayList<>();

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public ArrayList<User> getResults() {
        return Results;
    }

    public void setResults(ArrayList<User> results) {
        this.Results = results;
    }
}
