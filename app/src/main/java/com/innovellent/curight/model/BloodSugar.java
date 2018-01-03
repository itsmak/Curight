package com.innovellent.curight.model;

/**
 * Created by SUNIL on 1/3/2018.
 */

public class BloodSugar {

    private int aftermeal,beforemeal,bsid;
    private String bsFlag,date,graphflag;

    public BloodSugar(int aftermeal, int beforemeal, int bsid, String bsFlag, String date, String graphflag) {
        this.aftermeal = aftermeal;
        this.beforemeal = beforemeal;
        this.bsid = bsid;
        this.bsFlag = bsFlag;
        this.date = date;
        this.graphflag = graphflag;
    }


    public int getAftermeal() {
        return aftermeal;
    }

    public void setAftermeal(int aftermeal) {
        this.aftermeal = aftermeal;
    }

    public int getBeforemeal() {
        return beforemeal;
    }

    public void setBeforemeal(int beforemeal) {
        this.beforemeal = beforemeal;
    }

    public int getBsid() {
        return bsid;
    }

    public void setBsid(int bsid) {
        this.bsid = bsid;
    }

    public String getBsFlag() {
        return bsFlag;
    }

    public void setBsFlag(String bsFlag) {
        this.bsFlag = bsFlag;
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
}
