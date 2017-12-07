package com.innovellent.curight.model;

import java.util.ArrayList;


public class ServerResponseExercise {
    private Integer Code;
    private Exercise Results;

    public ServerResponseExercise(Integer code, Exercise results) {
        this.Code = code;
        this.Results = results;
    }

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer Code) {
        this.Code = Code;
    }

    public Exercise getResults() {
        return Results;
    }

    public void setResults(Exercise results) {
        Results = results;
    }
}
