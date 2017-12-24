package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mak on 12/19/2017.
 */

public class Profile_Response {

    @SerializedName("Code")
    private int code;

    @SerializedName("Results")
    ArrayList<PROFILE_RESPONSE_FEED> results = new ArrayList<>();

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<PROFILE_RESPONSE_FEED> getResults() {
        return results;
    }

    public void setResults(ArrayList<PROFILE_RESPONSE_FEED> results) {
        this.results = results;
    }
}
