package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CholesterolDayWise {

    @SerializedName("date")
    private String date;

    @SerializedName("cholestrolList")
    private List<CholesterolRecord> records;

    public CholesterolDayWise(String date, List<CholesterolRecord> records) {
        this.date = date;
        this.records = records;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<CholesterolRecord> getRecords() {
        return records;
    }

    public void setRecords(List<CholesterolRecord> records) {
        this.records = records;
    }
}
