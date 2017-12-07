package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BMIReport {

    @SerializedName("bmi")
    private int bmi;

    @SerializedName("bmiFlag")
    private String bmiFlag;

    @SerializedName("bmiList")
    private List<BMIDayWise> bmiList = null;

    public BMIReport(int bmi, String bmiFlag, List<BMIDayWise> bmiList) {
        this.bmi = bmi;
        this.bmiFlag = bmiFlag;
        this.bmiList = bmiList;
    }

    public int getBmi() {
        return bmi;
    }

    public void setBmi(int bmi) {
        this.bmi = bmi;
    }

    public String getBmiFlag() {
        return bmiFlag;
    }

    public void setBmiFlag(String bmiFlag) {
        this.bmiFlag = bmiFlag;
    }

    public List<BMIDayWise> getBmiList() {
        return bmiList;
    }

    public void setBmiList(List<BMIDayWise> bmiList) {
        this.bmiList = bmiList;
    }
}
