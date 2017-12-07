package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BloodPressureReport {

    @SerializedName("pulse")
    private Integer pulse;

    @SerializedName("systolic")
    private Integer systolic;

    @SerializedName("diastolic")
    private Integer diastolic;

    @SerializedName("bpFlag")
    private String bpFlag;

    @SerializedName("bpList")
    private List<BloodPressureDayWise> bpList = null;

    public BloodPressureReport(Integer pulse, Integer systolic, Integer diastolic, String bpFlag, List<BloodPressureDayWise> bpList) {
        this.pulse = pulse;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.bpFlag = bpFlag;
        this.bpList = bpList;
    }

    public Integer getPulse() {
        return pulse;
    }

    public void setPulse(Integer pulse) {
        this.pulse = pulse;
    }

    public Integer getSystolic() {
        return systolic;
    }

    public void setSystolic(Integer systolic) {
        this.systolic = systolic;
    }

    public Integer getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(Integer diastolic) {
        this.diastolic = diastolic;
    }

    public String getBpFlag() {
        return bpFlag;
    }

    public void setBpFlag(String bpFlag) {
        this.bpFlag = bpFlag;
    }

    public List<BloodPressureDayWise> getBpList() {
        return bpList;
    }

    public void setBpList(List<BloodPressureDayWise> bpList) {
        this.bpList = bpList;
    }
}
