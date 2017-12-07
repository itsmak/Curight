package com.innovellent.curight.model;

import java.util.ArrayList;

/**
 * Created by SUNIL on 12/6/2017.
 */

public class ServerResponseGetTestDetail {

    private String Code;
    private ArrayList<GetTestDetails> Results = new ArrayList<>();
    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public ArrayList<GetTestDetails> getResults() {
        return Results;
    }

    public void setResults(ArrayList<GetTestDetails> results) {
        Results = results;
    }

    public ServerResponseGetTestDetail(String code, ArrayList<GetTestDetails> results) {

        Code = code;
        Results = results;
    }


}
