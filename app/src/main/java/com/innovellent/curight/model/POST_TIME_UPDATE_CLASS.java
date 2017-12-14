package com.innovellent.curight.model;

/**
 * Created by Mak on 12/14/2017.
 */

public class POST_TIME_UPDATE_CLASS {

    private int medreminderparentid;
    private int medreminderchildid;
    private String date;
    private String morningmedstatus;
    private String noonmedstatus;
    private String eveningmedstatus;
    private String nightmedstatus;

    public POST_TIME_UPDATE_CLASS(int medreminderparentid, int medreminderchildid, String date, String morningmedstatus,String noonmedstatus, String eveningmedstatus, String nightmedstatus) {
        this.medreminderparentid = medreminderparentid;
        this.medreminderchildid = medreminderchildid;
        this.date = date;
        this.morningmedstatus = morningmedstatus;
        this.noonmedstatus = noonmedstatus;
        this.eveningmedstatus = eveningmedstatus;
        this.nightmedstatus = nightmedstatus;
    }

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMorningmedstatus() {
        return morningmedstatus;
    }

    public void setMorningmedstatus(String morningmedstatus) {
        this.morningmedstatus = morningmedstatus;
    }

    public String getNoonmedstatus() {
        return noonmedstatus;
    }

    public void setNoonmedstatus(String noonmedstatus) {
        this.noonmedstatus = noonmedstatus;
    }

    public String getEveningmedstatus() {
        return eveningmedstatus;
    }

    public void setEveningmedstatus(String eveningmedstatus) {
        this.eveningmedstatus = eveningmedstatus;
    }

    public String getNightmedstatus() {
        return nightmedstatus;
    }

    public void setNightmedstatus(String nightmedstatus) {
        this.nightmedstatus = nightmedstatus;
    }
}
