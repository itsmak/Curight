package com.innovellent.curight.model;

import java.util.ArrayList;


public class ServerResponseAuth {
    private Integer Code;
    private AccessToken Results;

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer Code) {
        this.Code = Code;
    }

    public AccessToken getResults() {
        return Results;
    }

    public void setResults(AccessToken results) {
        this.Results = results;
    }
}
