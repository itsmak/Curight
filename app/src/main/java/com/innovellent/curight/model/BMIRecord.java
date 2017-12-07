package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

public class BMIRecord {

    @SerializedName("bmiid")
    private int bmiid;

    @SerializedName("time")
    private String time;

    @SerializedName("bmi")
    private Double bmi;

    @SerializedName("height")
    private Double height;

    @SerializedName("weight")
    private Double weight;

    @SerializedName("bmiFlag")
    private String bmiFlag;

    public BMIRecord(int bmiid, String time, Double bmi, Double height, Double weight, String bmiFlag) {
        this.bmiid = bmiid;
        this.time = time;
        this.bmi = bmi;
        this.height = height;
        this.weight = weight;
        this.bmiFlag = bmiFlag;
    }

    public int getBmiid() {
        return bmiid;
    }

    public void setBmiid(int bmiid) {
        this.bmiid = bmiid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getBmi() {
        return bmi;
    }

    public void setBmi(Double bmi) {
        this.bmi = bmi;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getBmiFlag() {
        return bmiFlag;
    }

    public void setBmiFlag(String bmiFlag) {
        this.bmiFlag = bmiFlag;
    }
}
