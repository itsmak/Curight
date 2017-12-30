package com.innovellent.curight.model;

/**
 * Created by SUNIL on 12/30/2017.
 */

public class Cholesterol {

    private int hdl;
    private int ldl;
    private int cholestrolid;

    public int getTriglycerides() {
        return triglycerides;
    }

    public void setTriglycerides(int triglycerides) {
        this.triglycerides = triglycerides;
    }

    private int triglycerides;
    private String date,time,totalCholestrolFlag;

    public Cholesterol(int hdl, int ldl, int cholestrolid,int triglycerides,String date, String time, String totalCholestrolFlag) {
        this.hdl = hdl;
        this.ldl = ldl;
        this.triglycerides =triglycerides;
        this.cholestrolid = cholestrolid;
        this.date = date;
        this.time = time;
        this.totalCholestrolFlag = totalCholestrolFlag;
    }


    public int getHdl() {
        return hdl;
    }

    public void setHdl(int hdl) {
        this.hdl = hdl;
    }

    public int getLdl() {
        return ldl;
    }

    public void setLdl(int ldl) {
        this.ldl = ldl;
    }

    public int getCholestrolid() {
        return cholestrolid;
    }

    public void setCholestrolid(int cholestrolid) {
        this.cholestrolid = cholestrolid;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalCholestrolFlag() {
        return totalCholestrolFlag;
    }

    public void setTotalCholestrolFlag(String totalCholestrolFlag) {
        this.totalCholestrolFlag = totalCholestrolFlag;
    }
}
