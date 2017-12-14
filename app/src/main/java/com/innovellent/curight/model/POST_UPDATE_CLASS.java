package com.innovellent.curight.model;

/**
 * Created by Mak on 12/7/2017.
 */

public class POST_UPDATE_CLASS {

    private int userid;
    private int vaccineactivityid;
    private int vaccinechartid;
    private String vaccinedate;
    private String comments;
    private String doctorname;
    private String reminder1;
    private String reminder2;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getVaccineactivityid() {
        return vaccineactivityid;
    }

    public void setVaccineactivityid(int vaccineactivityid) {
        this.vaccineactivityid = vaccineactivityid;
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

    public String getReminder1() {
        return reminder1;
    }

    public void setReminder1(String reminder1) {
        this.reminder1 = reminder1;
    }

    public String getReminder2() {
        return reminder2;
    }

    public void setReminder2(String reminder2) {
        this.reminder2 = reminder2;
    }

    public int getVaccinechartid() {
        return vaccinechartid;
    }

    public void setVaccinechartid(int vaccinechartid) {
        this.vaccinechartid = vaccinechartid;
    }

    public POST_UPDATE_CLASS(int userid, int vaccineactivityid, int vaccinechartid , String vaccinedate, String comments, String doctorname, String reminder1, String reminder2) {
        this.userid = userid;
        this.vaccineactivityid = vaccineactivityid;
        this.vaccinechartid = vaccinechartid;
        this.vaccinedate = vaccinedate;
        this.comments = comments;
        this.doctorname = doctorname;
        this.reminder1 = reminder1;
        this.reminder2 = reminder2;
    }
}
