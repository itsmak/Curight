package com.innovellent.curight.model;

import java.util.ArrayList;

/**
 * Created by SUNIL on 12/7/2017.
 */

public class ServerResponsePhotosByDC {

    private String Code;
    private ArrayList<GetPhotosList> Results = new ArrayList<>();

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public ArrayList<GetPhotosList> getResults() {
        return Results;
    }

    public void setResults(ArrayList<GetPhotosList> results) {
        Results = results;
    }

    public ServerResponsePhotosByDC(String code, ArrayList<GetPhotosList> results) {

        Code = code;
        Results = results;
    }
}
