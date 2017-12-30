package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by SUNIL on 12/8/2017.
 */

public class ServerResponseWHRGet<T> {


    @SerializedName("Code")
    private int code;

    @SerializedName("Results")
    private T results;
    //ArrayList<WhrList> results = new ArrayList<>();

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
