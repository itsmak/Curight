package com.innovellent.curight.model;

/**
 * Created by SUNIL on 12/30/2017.
 */

public class WHR {

    private String whrFlag;
    private String date;
    private String graphflag;
    private String waistcircumference;
    private String hipcircumference;

    public String getWhr_subdata() {
        return whr_subdata;
    }

    public void setWhr_subdata(String whr_subdata) {
        this.whr_subdata = whr_subdata;
    }

    private String whr_subdata;
    private int whr,whrid;


    public WHR( String date,int whrid, String whr_subdata, String graphflag, String waistcircumference, String hipcircumference) {
        this.whrid = whrid;
        this.date = date;
        this.graphflag = graphflag;
        this.waistcircumference = waistcircumference;
        this.hipcircumference = hipcircumference;

        this.whr_subdata = whr_subdata;
    }

    public String getWhrFlag() {
        return whrFlag;
    }

    public void setWhrFlag(String whrFlag) {
        this.whrFlag = whrFlag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGraphflag() {
        return graphflag;
    }

    public void setGraphflag(String graphflag) {
        this.graphflag = graphflag;
    }

    public String getWaistcircumference() {
        return waistcircumference;
    }

    public void setWaistcircumference(String waistcircumference) {
        this.waistcircumference = waistcircumference;
    }

    public String getHipcircumference() {
        return hipcircumference;
    }

    public void setHipcircumference(String hipcircumference) {
        this.hipcircumference = hipcircumference;
    }

    public int getWhr() {
        return whr;
    }

    public void setWhr(int whr) {
        this.whr = whr;
    }

    public int getWhrid() {
        return whrid;
    }

    public void setWhrid(int whrid) {
        this.whrid = whrid;
    }
}
