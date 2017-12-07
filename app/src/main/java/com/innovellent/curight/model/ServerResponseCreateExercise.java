package com.innovellent.curight.model;

import java.util.ArrayList;


public class ServerResponseCreateExercise {
    private Integer Code;
    private String Results;

    public ServerResponseCreateExercise(Integer code, String results) {
        this.Code = code;
        this.Results = results;
    }

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer code) {
        Code = code;
    }

    public String getResults() {
        return Results;
    }

    public void setResults(String results) {
        Results = results;
    }
}
