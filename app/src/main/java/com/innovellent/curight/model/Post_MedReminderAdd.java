package com.innovellent.curight.model;

/**
 * Created by Mak on 12/22/2017.
 */

public class Post_MedReminderAdd {

    private int userid;
    private int medicineid;
    private String medicinename;
    private String strength;
    private String dose;
    private String how;
    private String startdate;
    private String durationday;
    private String durationtype;
    private String lifetime;
    private String morningtime;
    private String noontime;
    private String eveningtime;
    private String nighttime;
    private String notes;

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

    public String getHow() {
        return how;
    }

    public void setHow(String how) {
        this.how = how;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getDurationday() {
        return durationday;
    }

    public void setDurationday(String durationday) {
        this.durationday = durationday;
    }

    public String getDurationtype() {
        return durationtype;
    }

    public void setDurationtype(String durationtype) {
        this.durationtype = durationtype;
    }

    public String getLifetime() {
        return lifetime;
    }

    public void setLifetime(String lifetime) {
        this.lifetime = lifetime;
    }

    public String getMorningtime() {
        return morningtime;
    }

    public void setMorningtime(String morningtime) {
        this.morningtime = morningtime;
    }

    public String getNoontime() {
        return noontime;
    }

    public void setNoontime(String noontime) {
        this.noontime = noontime;
    }

    public String getEveningtime() {
        return eveningtime;
    }

    public void setEveningtime(String eveningtime) {
        this.eveningtime = eveningtime;
    }

    public String getNighttime() {
        return nighttime;
    }

    public void setNighttime(String nighttime) {
        this.nighttime = nighttime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Post_MedReminderAdd(int userid, int medicineid, String medicinename, String strength, String dose, String how,
    String startdate, String durationday, String durationtype, String lifetime, String morningtime, String noontime, String eveningtime, String nighttime, String notes) {
        this.userid = userid;
        this.medicineid = medicineid;
        this.medicinename = medicinename;
        this.strength = strength;
        this.dose = dose;
        this.how = how;
        this.startdate = startdate;
        this.durationday = durationday;
        this.durationtype = durationtype;
        this.lifetime = lifetime;
        this.morningtime = morningtime;
        this.noontime = noontime;
        this.eveningtime = eveningtime;
        this.nighttime = nighttime;
        this.notes = notes;
    }
}
