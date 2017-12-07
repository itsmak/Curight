package com.innovellent.curight.model;

import java.util.ArrayList;

/**
 * Created by SUNIL on 12/4/2017.
 */

public class ServerResponseDoctorAppointment {

    private String Code;
    private ArrayList<DoctorList> Results=new ArrayList<>();

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public ArrayList<DoctorList> getResults() {
        return Results;
    }

    public void setResults(ArrayList<DoctorList> results) {
        this.Results = results;
    }
}
