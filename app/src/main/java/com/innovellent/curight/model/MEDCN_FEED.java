package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mak on 12/12/2017.
 */

public class MEDCN_FEED {

    @SerializedName("medreminderparentid")
    private int medreminderparentid;

    @SerializedName("medreminderchildid")
    private int medreminderchildid;

    @SerializedName("userid")
    private int userid;

    @SerializedName("medicineid")
    private int medicineid;

    @SerializedName("medicinename")
    private String medicinename;

    @SerializedName("medicinetakendate")
    private String medicinetakendate;

    @SerializedName("strength")
    private String strength;

    @SerializedName("dose")
    private String dose;

    @SerializedName("durationday")
    private int durationday;

    @SerializedName("morningtime")
    private String morningtime;

    @SerializedName("morningmedstatus")
    private String morningmedstatus;

    @SerializedName("noontime")
    private String noontime;

    @SerializedName("noonmedstatus")
    private String noonmedstatus;

    @SerializedName("eveningtime")
    private String eveningtime;

    @SerializedName("eveninmedstatus")
    private String eveninmedstatus;

    @SerializedName("nighttime")
    private String nighttime;

    @SerializedName("nightmedstatus")
    private String nightmedstatus;

    public int getMedreminderparentid() {
        return medreminderparentid;
    }

    public void setMedreminderparentid(int medreminderparentid) {
        this.medreminderparentid = medreminderparentid;
    }

    public int getMedreminderchildid() {
        return medreminderchildid;
    }

    public void setMedreminderchildid(int medreminderchildid) {
        this.medreminderchildid = medreminderchildid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getMedicineid() {
        return medicineid;
    }

    public void setMedicineid(int medicineid) {
        this.medicineid = medicineid;
    }

    public String getMedicinename() {
        return medicinename;
    }

    public void setMedicinename(String medicinename) {
        this.medicinename = medicinename;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public int getDurationday() {
        return durationday;
    }

    public void setDurationday(int durationday) {
        this.durationday = durationday;
    }

    public String getMorningtime() {
        return morningtime;
    }

    public void setMorningtime(String morningtime) {
        this.morningtime = morningtime;
    }

    public String getMorningmedstatus() {
        return morningmedstatus;
    }

    public void setMorningmedstatus(String morningmedstatus) {
        this.morningmedstatus = morningmedstatus;
    }

    public String getNoontime() {
        return noontime;
    }

    public void setNoontime(String noontime) {
        this.noontime = noontime;
    }

    public String getNoonmedstatus() {
        return noonmedstatus;
    }

    public void setNoonmedstatus(String noonmedstatus) {
        this.noonmedstatus = noonmedstatus;
    }

    public String getEveningtime() {
        return eveningtime;
    }

    public void setEveningtime(String eveningtime) {
        this.eveningtime = eveningtime;
    }

    public String getEveninmedstatus() {
        return eveninmedstatus;
    }

    public void setEveninmedstatus(String eveninmedstatus) {
        this.eveninmedstatus = eveninmedstatus;
    }

    public String getNighttime() {
        return nighttime;
    }

    public void setNighttime(String nighttime) {
        this.nighttime = nighttime;
    }

    public String getNightmedstatus() {
        return nightmedstatus;
    }

    public void setNightmedstatus(String nightmedstatus) {
        this.nightmedstatus = nightmedstatus;
    }

    public String getMedicinetakendate() {
        return medicinetakendate;
    }

    public void setMedicinetakendate(String medicinetakendate) {
        this.medicinetakendate = medicinetakendate;
    }
}
