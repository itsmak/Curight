package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mak on 4/12/2018.
 */

public class ServerResponseGoalnew<T>{

    @SerializedName("Code")
    private int code;

    @SerializedName("Results")
    private Goal results;

    public ServerResponseGoalnew(Integer code, Goal results) {
        this.code = code;
        this.results = results;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Goal getResults() {
        return results;
    }

    public void setResults(Goal results) {
        this.results = results;
    }
}
