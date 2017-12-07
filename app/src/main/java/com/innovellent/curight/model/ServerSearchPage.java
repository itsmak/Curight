package com.innovellent.curight.model;

import java.util.ArrayList;

/**
 * Created by SUNIL on 12/4/2017.
 */

public class ServerSearchPage {

    private String Code;
    private ArrayList<Search> Results=new ArrayList<>();

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public ArrayList<Search> getSearchResults() {
        return Results;
    }

    public void setResults(ArrayList<Search> results) {
        this.Results = results;
    }
}
