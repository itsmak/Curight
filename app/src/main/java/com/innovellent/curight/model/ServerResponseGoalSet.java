package com.innovellent.curight.model;


public class ServerResponseGoalSet {
    private Integer Code;
    private String Results;

    public ServerResponseGoalSet(Integer code, String results) {
        this.Code = code;
        this.Results = results;
    }

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer Code) {
        this.Code = Code;
    }

    public String getResults() {
        return Results;
    }

    public void setResults(String results) {
        Results = results;
    }
}
