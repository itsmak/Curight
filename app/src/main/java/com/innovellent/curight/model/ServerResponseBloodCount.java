package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SUNIL on 12/21/2017.
 */

public class ServerResponseBloodCount<T> {


    @SerializedName("Code")
    private Integer code;

    @SerializedName("Results")
    private T results;

    public ServerResponseBloodCount(Integer code, T results) {
        this.code = code;
        this.results = results;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
