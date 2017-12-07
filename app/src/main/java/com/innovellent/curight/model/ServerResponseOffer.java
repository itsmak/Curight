package com.innovellent.curight.model;

import java.util.ArrayList;


public class ServerResponseOffer {
    private String code;
    private ArrayList<Offer> results=new ArrayList<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<Offer> getResults() {
        return results;
    }

    public void setResults(ArrayList<Offer> results) {
        this.results = results;
    }
}
