package com.innovellent.curight.model;

import java.util.ArrayList;


public class ServerResponseCalorie {
    private Integer Code;
    private ArrayList<CalorieRes> Results=new ArrayList<>();

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer Code) {
        this.Code = Code;
    }

    public ArrayList<CalorieRes> getResults() {
        return Results;
    }

    public void setResults(ArrayList<CalorieRes> results) {
        this.Results = results;
    }
}
