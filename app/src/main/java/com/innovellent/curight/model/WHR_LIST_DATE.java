package com.innovellent.curight.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SUNIL on 12/8/2017.
 */

public class WHR_LIST_DATE {


    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("whrList")
    @Expose
    private ArrayList<WHR_LIST> whrList;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<WHR_LIST> getWhrList() {
        return whrList;
    }

    public void setWhrList(ArrayList<WHR_LIST> whrList) {
        this.whrList = whrList;
    }
}
