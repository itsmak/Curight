package com.innovellent.curight.model;

/**
 * Created by Pramod on 31-10-2017.
 */

public class User {
    private Long userid;
    private String name;
    private Long patientid;
    private String mobile;
    private String email;
    private Long otp;

    public User(Long userid, String name, Long patientid, String mobile, String email, Long otp) {
        this.userid = userid;
        this.name = name;
        this.patientid = patientid;
        this.mobile = mobile;
        this.email = email;
        this.otp = otp;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPatientid() {
        return patientid;
    }

    public void setPatientid(Long patientid) {
        this.patientid = patientid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getOtp() {
        return otp;
    }

    public void setOtp(Long otp) {
        this.otp = otp;
    }
}
