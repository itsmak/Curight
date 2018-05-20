package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mak on 5/20/2018.
 */

public class ServerResponseCoupon {

    @SerializedName("Code")
    private Integer Code;
    @SerializedName("Results")
    private ArrayList<CouponRes> Results=new ArrayList<>();

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer code) {
        Code = code;
    }

    public ArrayList<CouponRes> getResults() {
        return Results;
    }

    public void setResults(ArrayList<CouponRes> results) {
        Results = results;
    }
}
