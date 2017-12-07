package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BMIDayWise {

    @SerializedName("date")
    private String date;

    @SerializedName("bmiList")
    private List<BMIRecord> records;

    public BMIDayWise(String date, List<BMIRecord> records) {
        this.date = date;
        this.records = records;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<BMIRecord> getRecords() {
        return records;
    }

    public void setRecords(List<BMIRecord> records) {
        this.records = records;
    }
}
