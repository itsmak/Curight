package com.innovellent.curight.model;

import java.util.ArrayList;

/**
 * Created by Pramod on 31-10-2017.
 */

public class Center {
    private Long diagnosticcentreid;
    private String diagnosticcentrename;
    private String specializationname;
    private String tests;
    private String tagline;
    private String address;
    private String city;
    private String photourl;
    private String accredition;
    private String count;
    private String normalworkingschedule;
    private String logourl;
    private String weekendworkingschedule;
    private Long mobile;
    private ArrayList<TestDetail> testDetail;

    public Center(Long diagnosticcentreid, String diagnosticcentrename, String specializationname, String tests, String tagline, String address, String city, String photourl, String accredition, String count, String normalworkingschedule, String logourl, String weekendworkingschedule, Long mobile, ArrayList<TestDetail> testDetail) {
        this.diagnosticcentreid = diagnosticcentreid;
        this.diagnosticcentrename = diagnosticcentrename;
        this.specializationname = specializationname;
        this.tests = tests;
        this.tagline = tagline;
        this.address = address;
        this.city = city;
        this.photourl = photourl;
        this.accredition = accredition;
        this.count = count;
        this.normalworkingschedule = normalworkingschedule;
        this.logourl = logourl;
        this.weekendworkingschedule = weekendworkingschedule;
        this.mobile = mobile;
        this.testDetail = testDetail;
    }

    public Long getDiagnosticcentreid() {
        return diagnosticcentreid;
    }

    public void setDiagnosticcentreid(Long diagnosticcentreid) {
        this.diagnosticcentreid = diagnosticcentreid;
    }

    public String getDiagnosticcentrename() {
        return diagnosticcentrename;
    }

    public void setDiagnosticcentrename(String diagnosticcentrename) {
        this.diagnosticcentrename = diagnosticcentrename;
    }

    public String getSpecializationname() {
        return specializationname;
    }

    public void setSpecializationname(String specializationname) {
        this.specializationname = specializationname;
    }

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public String getAccredition() {
        return accredition;
    }

    public void setAccredition(String accredition) {
        this.accredition = accredition;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getNormalworkingschedule() {
        return normalworkingschedule;
    }

    public void setNormalworkingschedule(String normalworkingschedule) {
        this.normalworkingschedule = normalworkingschedule;
    }

    public String getLogourl() {
        return logourl;
    }

    public void setLogourl(String logourl) {
        this.logourl = logourl;
    }

    public String getWeekendworkingschedule() {
        return weekendworkingschedule;
    }

    public void setWeekendworkingschedule(String weekendworkingschedule) {
        this.weekendworkingschedule = weekendworkingschedule;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public ArrayList<TestDetail> getTestDetail() {
        return testDetail;
    }

    public void setTestDetail(ArrayList<TestDetail> testDetail) {
        this.testDetail = testDetail;
    }
}
