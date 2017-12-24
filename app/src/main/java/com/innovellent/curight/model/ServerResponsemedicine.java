package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mak on 12/21/2017.
 */

public class ServerResponsemedicine {

    @SerializedName("Code")
    private int code;

    @SerializedName("Results")
    ArrayList<Medicine_List_Feed> results = new ArrayList<>();

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<Medicine_List_Feed> getResults() {
        return results;
    }

    public void setResults(ArrayList<Medicine_List_Feed> results) {
        this.results = results;
    }
}
