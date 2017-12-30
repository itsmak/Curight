package com.innovellent.curight.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SUNIL on 12/8/2017.
 */

public class WHR_LIST {


        @SerializedName("whrid")
        @Expose
        private int whrid;

        @SerializedName("whr")
        @Expose
        private String whr_subdata;

    @SerializedName("graphflag")
    @Expose
    private String graphflag;

    @SerializedName("waistcircumference")
    @Expose
    private String waistcircumference;

    @SerializedName("hipcircumference")
    @Expose
    private String hipcircumference;

    /*@SerializedName("whrFlag")
    @Expose
    private String whrFlag;*/


    public String getGraphflag() {
        return graphflag;
    }

    public void setGraphflag(String graphflag) {
        this.graphflag = graphflag;
    }


    public int getWhrid() {
        return whrid;
    }

    public void setWhrid(int whrid) {
        this.whrid = whrid;
    }

    public String getWhr_subdata() {
        return whr_subdata;
    }

    public void setWhr_subdata(String whr_subdata) {
        this.whr_subdata = whr_subdata;
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

    /*public String getWhrFlag() {
        return whrFlag;
    }

    public void setWhrFlag(String whrFlag) {
        this.whrFlag = whrFlag;
    }*/






}
