package com.innovellent.curight.model;

import java.util.ArrayList;


public class ServerResponseBookedTest {
    private String Code;
    private ArrayList<BookedTest> Results=new ArrayList<>();

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public ArrayList<BookedTest> getResults() {
        return Results;
    }

    public void setResults(ArrayList<BookedTest> results) {
        this.Results = results;
    }
}
