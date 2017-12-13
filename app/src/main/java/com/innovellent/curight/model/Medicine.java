package com.innovellent.curight.model;

/**
 * Created by sagar on 9/19/2017.
 */

public class Medicine {
    private String medicinename;
    private String medicinemeasure;
    private String morningtime;
    private String morningmedstatus;
    private String noontime;
    private String noonmedstatus;
    private String eveningtime;
    private String eveninmedstatus;
    private String nighttime;
    private String nightmedstatus;

    public Medicine(String medicinename,String medicinemeasure,String morningtime,String morningmedstatus,String noontime,String noonmedstatus,
            String eveningtime,String eveninmedstatus,String nighttime,String nightmedstatus){
        this.medicinemeasure=medicinemeasure;
        this.medicinename=medicinename;
        this.morningtime=morningtime;
        this.morningmedstatus=morningmedstatus;
        this.noontime=noontime;
        this.noonmedstatus=noonmedstatus;
        this.eveningtime=eveningtime;
        this.eveninmedstatus=eveninmedstatus;
        this.nighttime=nighttime;
        this.nightmedstatus=nightmedstatus;
    }
    public String getMedicinename() {
        return medicinename;
    }

    public void setMedicinename(String medicinename) {
        this.medicinename = medicinename;
    }

    public String getMedicinemeasure() {
        return medicinemeasure;
    }

    public void setMedicinemeasure(String medicinemeasure) {
        this.medicinemeasure = medicinemeasure;
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
}
