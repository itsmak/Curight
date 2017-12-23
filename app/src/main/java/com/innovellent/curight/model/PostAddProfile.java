package com.innovellent.curight.model;

/**
 * Created by Mak on 12/20/2017.
 */

public class PostAddProfile {

    private String name;
    private String parentid;
    private String dob;
    private String relationship;
    private String gender;
    private String bloodgroup;
    private String mobile;
    private String email;
    private String city;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PostAddProfile(String name, String parentid, String dob, String relationship, String gender, String bloodgroup, String mobile,
                          String email, String city, String address) {
        this.name = name;
        this.parentid = parentid;
        this.dob = dob;
        this.relationship = relationship;
        this.gender = gender;
        this.bloodgroup = bloodgroup;
        this.mobile = mobile;
        this.email = email;
        this.city = city;
        this.address = address;
    }
}
