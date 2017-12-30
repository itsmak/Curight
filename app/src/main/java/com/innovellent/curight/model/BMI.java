package com.innovellent.curight.model;

/**
 * Created by SUNIL on 12/31/2017.
 */

public class BMI {
    private String date,height,weight,bmi;
    private int bmiid;

    public BMI(String date, String height, String weight, String bmi, int bmiid) {
        this.date = date;
        this.height = height;
        this.weight = weight;
        this.bmi = bmi;
        this.bmiid = bmiid;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public int getBmiid() {
        return bmiid;
    }

    public void setBmiid(int bmiid) {
        this.bmiid = bmiid;
    }
}
