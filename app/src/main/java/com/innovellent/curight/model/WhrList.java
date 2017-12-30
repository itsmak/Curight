package com.innovellent.curight.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SUNIL on 12/8/2017.
 */

public class WhrList {


    @SerializedName("whrFlag")
    @Expose
    private String whrFlag;

    @SerializedName("whr")
    @Expose
    private int whr;

    @SerializedName("whrdtoList")
    @Expose
    private ArrayList<WHR_LIST_DATE> whrdtoList = null;

    public String getWhrFlag() {
        return whrFlag;
    }

    public void setWhrFlag(String whrFlag) {
        this.whrFlag = whrFlag;
    }

    public int getWhr() {
        return whr;
    }

    public void setWhr(int whr) {
        this.whr = whr;
    }

    public ArrayList<WHR_LIST_DATE> getWhrdtoList() {
        return whrdtoList;
    }

    public void setWhrdtoList(ArrayList<WHR_LIST_DATE> whrdtoList) {
        this.whrdtoList = whrdtoList;
    }
}
