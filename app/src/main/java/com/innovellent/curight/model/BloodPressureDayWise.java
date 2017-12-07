package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BloodPressureDayWise {

    @SerializedName("date")
    private String date;

    @SerializedName("bpList")
    private List<BloodPressureRecord> bpList;

    public BloodPressureDayWise(String date, List<BloodPressureRecord> bpList) {
        this.date = date;
        this.bpList = bpList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<BloodPressureRecord> getBpList() {
        return bpList;
    }

    public void setBpList(List<BloodPressureRecord> bpList) {
        this.bpList = bpList;
    }
}
