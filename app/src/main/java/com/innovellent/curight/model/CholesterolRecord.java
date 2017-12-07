package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

public class CholesterolRecord {

    @SerializedName("cholestrolid")
    private Integer cholesterolId;

    @SerializedName("time")
    private String time;

    @SerializedName("hdl")
    private Integer hdl;

    @SerializedName("ldl")
    private Integer ldl;

    @SerializedName("triglycerides")
    private Integer triglycerides;

    @SerializedName("totalCholestrolFlag")
    private String totalCholesterolFlag;

    public CholesterolRecord(Integer cholesterolId, String time, Integer hdl, Integer ldl, Integer triglycerides, String totalCholesterolFlag) {
        this.cholesterolId = cholesterolId;
        this.time = time;
        this.hdl = hdl;
        this.ldl = ldl;
        this.triglycerides = triglycerides;
        this.totalCholesterolFlag = totalCholesterolFlag;
    }

    public Integer getCholesterolId() {
        return cholesterolId;
    }

    public void setCholesterolId(Integer cholesterolId) {
        this.cholesterolId = cholesterolId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getTotalCholesterolFlag() {
        return totalCholesterolFlag;
    }

    public void setTotalCholesterolFlag(String totalCholesterolFlag) {
        this.totalCholesterolFlag = totalCholesterolFlag;
    }
}
