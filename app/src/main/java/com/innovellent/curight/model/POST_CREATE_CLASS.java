package com.innovellent.curight.model;

/**
 * Created by Mak on 12/11/2017.
 */

public class POST_CREATE_CLASS {

    private int userid;
    private String ageinmonth;
    private String graceperiod;
    private String vaccinename;
    private String yearly;
    private String vaccineduedate;
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

    public String getAgeinmonth() {
        return ageinmonth;
    }

    public void setAgeinmonth(String ageinmonth) {
        this.ageinmonth = ageinmonth;
    }

    public String getGraceperiod() {
        return graceperiod;
    }

    public void setGraceperiod(String graceperiod) {
        this.graceperiod = graceperiod;
    }

    public String getVaccinename() {
        return vaccinename;
    }

    public void setVaccinename(String vaccinename) {
        this.vaccinename = vaccinename;
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

    public POST_CREATE_CLASS(int userid, String ageinmonth, String graceperiod, String vaccinename, String yearly,
       String vaccineduedate, String vaccinedate, String comments, String doctorname, String reminder1, String reminder2) {
        this.userid = userid;
        this.ageinmonth = ageinmonth;
        this.graceperiod = graceperiod;
        this.vaccinename = vaccinename;
        this.yearly = yearly;
        this.vaccineduedate = vaccineduedate;
        this.vaccinedate = vaccinedate;
        this.comments = comments;
        this.doctorname = doctorname;
        this.reminder1 = reminder1;
        this.reminder2 = reminder2;
    }
}
