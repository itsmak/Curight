package com.innovellent.curight.model;

/**
 * Created by sagar on 9/13/2017.
 */

public class Vaccine {
    private String duration_age;
    private String vaccinename;
    private String date;
    private String doctorname;
    private String comments;
    private String duedate;
    private String ageinonth;
    private String userid;
    private String vaccineactivityid;
    private boolean visiblity;

    public Vaccine(String vaccinename, String date){
        this.vaccinename=vaccinename;
        this.date=date;
    }

    public Vaccine(String duration_age, String vaccinename, String date, String duedate,
                   String doctorname, String comments, String ageinonth,String userid,String vaccineactivityid, boolean visiblity) {
        this.duration_age = duration_age;
        this.vaccinename = vaccinename;
        this.date = date;
        this.duedate = duedate;
        this.doctorname = doctorname;
        this.comments = comments;
        this.ageinonth = ageinonth;
        this.userid = userid;
        this.vaccineactivityid = vaccineactivityid;
        this.visiblity = visiblity;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public boolean isVisiblity() {
        return visiblity;
    }

    public void setVisiblity(boolean visiblity) {
        this.visiblity = visiblity;
    }

    public String getVaccinename() {
        return vaccinename;
    }

    public void setVaccinename(String vaccinename) {
        this.vaccinename = vaccinename;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration_age() {
        return duration_age;
    }

    public void setDuration_age(String duration_age) {
        this.duration_age = duration_age;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getAgeinonth() {
        return ageinonth;
    }

    public void setAgeinonth(String ageinonth) {
        this.ageinonth = ageinonth;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getVaccineactivityid() {
        return vaccineactivityid;
    }

    public void setVaccineactivityid(String vaccineactivityid) {
        this.vaccineactivityid = vaccineactivityid;
    }
}
