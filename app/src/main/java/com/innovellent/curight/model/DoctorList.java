package com.innovellent.curight.model;

/**
 * Created by SUNIL on 12/4/2017.
 */

public class DoctorList {

    private String doctorid,doctorname,specialization,tagline,clinicname,normalworkingschedule,weekendworkingschedule,addresscentre,city,pincode,email,mobile,modifiedby;

    public DoctorList(String doctorid, String doctorname, String specialization, String tagline, String clinicname, String normalworkingschedule, String weekendworkingschedule, String addresscentre, String city, String pincode, String email, String mobile, String modifiedby) {
        this.doctorid = doctorid;
        this.doctorname = doctorname;
        this.specialization = specialization;
        this.tagline = tagline;
        this.clinicname = clinicname;
        this.normalworkingschedule = normalworkingschedule;
        this.weekendworkingschedule = weekendworkingschedule;
        this.addresscentre = addresscentre;
        this.city = city;
        this.pincode = pincode;
        this.email = email;
        this.mobile = mobile;
        this.modifiedby = modifiedby;
    }

    public String getDoctorid() {

        return doctorid;
    }

    public void setDoctorid(String doctorid) {
        this.doctorid = doctorid;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getClinicname() {
        return clinicname;
    }

    public void setClinicname(String clinicname) {
        this.clinicname = clinicname;
    }

    public String getNormalworkingschedule() {
        return normalworkingschedule;
    }

    public void setNormalworkingschedule(String normalworkingschedule) {
        this.normalworkingschedule = normalworkingschedule;
    }

    public String getWeekendworkingschedule() {
        return weekendworkingschedule;
    }

    public void setWeekendworkingschedule(String weekendworkingschedule) {
        this.weekendworkingschedule = weekendworkingschedule;
    }

    public String getAddresscentre() {
        return addresscentre;
    }

    public void setAddresscentre(String addresscentre) {
        this.addresscentre = addresscentre;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
    }
}
