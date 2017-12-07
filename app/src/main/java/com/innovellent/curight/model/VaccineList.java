package com.innovellent.curight.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mak on 12/1/2017.
 */

public class VaccineList {

    @SerializedName("vaccinechartid")
    @Expose
    private Long vaccinechartid;

    @SerializedName("vaccineactivityid")
    @Expose
    private int vaccineactivityid;

    @SerializedName("userid")
    @Expose
    private int userid;

    @SerializedName("vaccinename")
    @Expose
    private String vaccinename;

    @SerializedName("graceperiod")
    @Expose
    private Long graceperiod;

    @SerializedName("yearly")
    @Expose
    private String yearly;

    @SerializedName("vaccineduedate")
    @Expose
    private String vaccineduedate;

    @SerializedName("vaccinedate")
    @Expose
    private String vaccinedate;

    @SerializedName("comments")
    @Expose
    private String comments;

    @SerializedName("doctorname")
    @Expose
    private String doctorname;

    @SerializedName("status")
    @Expose
    private String status;

    public Long getVaccinechartid() {
        return vaccinechartid;
    }

    public void setVaccinechartid(Long vaccinechartid) {
        this.vaccinechartid = vaccinechartid;
    }

    public int getVaccineactivityid() {
        return vaccineactivityid;
    }

    public void setVaccineactivityid(int vaccineactivityid) {
        this.vaccineactivityid = vaccineactivityid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getVaccinename() {
        return vaccinename;
    }

    public void setVaccinename(String vaccinename) {
        this.vaccinename = vaccinename;
    }

    public Long getGraceperiod() {
        return graceperiod;
    }

    public void setGraceperiod(Long graceperiod) {
        this.graceperiod = graceperiod;
    }

    public String getYearly() {
        return yearly;
    }

    public void setYearly(String yearly) {
        this.yearly = yearly;
    }

    public String getVaccineduedate() {
        return vaccineduedate;
    }

    public void setVaccineduedate(String vaccineduedate) {
        this.vaccineduedate = vaccineduedate;
    }

    public String getVaccinedate() {
        return vaccinedate;
    }

    public void setVaccinedate(String vaccinedate) {
        this.vaccinedate = vaccinedate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "VaccineList{" +
                "vaccinechartid=" + vaccinechartid +
                ", vaccineactivityid=" + vaccineactivityid +
                ", userid=" + userid +
                ", vaccinename='" + vaccinename + '\'' +
                ", graceperiod=" + graceperiod +
                ", yearly='" + yearly + '\'' +
                ", vaccineduedate='" + vaccineduedate + '\'' +
                ", vaccinedate='" + vaccinedate + '\'' +
                ", comments='" + comments + '\'' +
                ", doctorname='" + doctorname + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
