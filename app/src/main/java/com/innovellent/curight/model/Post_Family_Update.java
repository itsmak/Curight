package com.innovellent.curight.model;

/**
 * Created by Mak on 12/24/2017.
 */

public class Post_Family_Update {

    private int parentid;
    private int familyid;
    private String name;
    private String gender;
    private String email;
    private String bloodgroup;
    private String dob;
    private int pin;
    private String address;
    private String city;
    private String state;
    private String photourl;
    private String relationship;

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public int getFamilyid() {
        return familyid;
    }

    public void setFamilyid(int familyid) {
        this.familyid = familyid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public Post_Family_Update(int parentid, int familyid, String name, String gender, String email, String bloodgroup, String dob,
    int pin, String address, String city, String state, String photourl, String relationship) {
        this.parentid = parentid;
        this.familyid = familyid;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.bloodgroup = bloodgroup;
        this.dob = dob;
        this.pin = pin;
        this.address = address;
        this.city = city;
        this.state = state;
        this.photourl = photourl;
        this.relationship = relationship;
    }
}
