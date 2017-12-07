package com.innovellent.curight.model;

import java.util.ArrayList;


public class ServerResponseFood {
    private String Code;
    private Food Results;

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public Food getResults() {
        return Results;
    }

    public void setResults(Food results) {
        this.Results = results;
    }
}
