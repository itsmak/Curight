package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

public class BloodPressureRecord {

    @SerializedName("bpid")
    private Integer bpId;

    @SerializedName("time")
    private String time;

    @SerializedName("pulse")
    private Integer pulse;

    @SerializedName("systolic")
    private Integer systolic;

    @SerializedName("bpFlag")
    private String bpFlag;

    @SerializedName("diastolic")
    private Integer diastolic;

    public BloodPressureRecord(Integer bpId, String time, Integer pulse, Integer systolic, String bpFlag, Integer diastolic) {
        this.bpId = bpId;
        this.time = time;
        this.pulse = pulse;
        this.systolic = systolic;
        this.bpFlag = bpFlag;
        this.diastolic = diastolic;
    }

    public Integer getBpId() {
        return bpId;
    }

    public void setBpId(Integer bpId) {
        this.bpId = bpId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getBpFlag() {
        return bpFlag;
    }

    public void setBpFlag(String bpFlag) {
        this.bpFlag = bpFlag;
    }

    public Integer getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(Integer diastolic) {
        this.diastolic = diastolic;
    }
}
