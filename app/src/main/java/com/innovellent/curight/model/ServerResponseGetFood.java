package com.innovellent.curight.model;


import java.util.ArrayList;

public class ServerResponseGetFood {
    private Integer Code;
    private ArrayList<FoodDetail> Results;

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer Code) {
        this.Code = Code;
    }

    public ArrayList<FoodDetail> getResults() {
        return Results;
    }

    public void setResults(ArrayList<FoodDetail> results) {
        this.Results = results;
    }
}
