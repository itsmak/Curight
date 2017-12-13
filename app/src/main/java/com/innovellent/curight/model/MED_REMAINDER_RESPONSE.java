package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mak on 12/12/2017.
 */

public class MED_REMAINDER_RESPONSE {

    @SerializedName("Code")
    private int code;

    @SerializedName("Results")
    ArrayList<MEDCN_FEED> results = new ArrayList<>();

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<MEDCN_FEED> getResults() {
        return results;
    }

    public void setResults(ArrayList<MEDCN_FEED> results) {
        this.results = results;
    }
}
