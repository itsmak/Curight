package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CholesterolReport {
    @SerializedName("hdl")
    private Integer hdl;

    @SerializedName("ldl")
    private Integer ldl;

    @SerializedName("triglycerides")
    private Integer triglycerides;

    @SerializedName("cholestrolFlag")
    private String cholesterolFlag;

    @SerializedName("cholestrolbpList")
    private List<CholesterolDayWise> dayWises;

    public CholesterolReport(Integer hdl, Integer ldl, Integer triglycerides, String cholesterolFlag, List<CholesterolDayWise> dayWises) {
        this.hdl = hdl;
        this.ldl = ldl;
        this.triglycerides = triglycerides;
        this.cholesterolFlag = cholesterolFlag;
        this.dayWises = dayWises;
    }

    public Integer getHdl() {
        return hdl;
    }

    public void setHdl(Integer hdl) {
        this.hdl = hdl;
    }

    public Integer getLdl() {
        return ldl;
    }

    public void setLdl(Integer ldl) {
        this.ldl = ldl;
    }

    public Integer getTriglycerides() {
        return triglycerides;
    }

    public void setTriglycerides(Integer triglycerides) {
        this.triglycerides = triglycerides;
    }

    public String getCholesterolFlag() {
        return cholesterolFlag;
    }

    public void setCholesterolFlag(String cholesterolFlag) {
        this.cholesterolFlag = cholesterolFlag;
    }

    public List<CholesterolDayWise> getDayWises() {
        return dayWises;
    }

    public void setDayWises(List<CholesterolDayWise> dayWises) {
        this.dayWises = dayWises;
    }
}
