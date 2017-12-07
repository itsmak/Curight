package com.innovellent.curight.model;

import java.util.ArrayList;

/**
 * Created by SUNIL on 12/6/2017.
 */

public class ServerResponseDoctorByDC {

    private String Code;
    private ArrayList<DoctorByDC> Results = new ArrayList<>();

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public ArrayList<DoctorByDC> getResults() {
        return Results;
    }

    public void setResults(ArrayList<DoctorByDC> results) {
        Results = results;
    }

    public ServerResponseDoctorByDC(String code, ArrayList<DoctorByDC> results) {

        Code = code;
        Results = results;
    }
}