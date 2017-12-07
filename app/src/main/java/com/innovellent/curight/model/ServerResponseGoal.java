package com.innovellent.curight.model;


public class ServerResponseGoal {
    private Integer Code;
    private Goal Results;

    public ServerResponseGoal(Integer code, Goal results) {
        this.Code = code;
        this.Results = results;
    }

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer Code) {
        this.Code = Code;
    }

    public Goal getResults() {
        return Results;
    }

    public void setResults(Goal results) {
        Results = results;
    }
}
