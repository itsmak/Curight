package com.innovellent.curight.model;

import java.util.ArrayList;

/**
 * Created by SUNIL on 12/7/2017.
 */

public class ServerResponseOverviewByDC {

    private String Code;
    private ArrayList<GetOverviewList> Results = new ArrayList<>();
    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public ArrayList<GetOverviewList> getResults() {
        return Results;
    }

    public void setResults(ArrayList<GetOverviewList> results) {
        Results = results;
    }

    public ServerResponseOverviewByDC(String code, ArrayList<GetOverviewList> results) {

        Code = code;
        Results = results;
    }


}
