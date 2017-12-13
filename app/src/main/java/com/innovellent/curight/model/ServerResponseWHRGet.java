package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by SUNIL on 12/8/2017.
 */

public class ServerResponseWHRGet<T> {


    @SerializedName("Code")
    private Integer code;

    @SerializedName("Results")
     WhrList Results;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public WhrList getResults() {
        return Results;
    }

    public void setResults(WhrList results) {
        Results = results;
    }
}
