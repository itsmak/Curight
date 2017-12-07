package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

public class FamilyProfile {


    @SerializedName("id")
    private int id;

    @SerializedName("userid")
    private String userId;

    @SerializedName("patientid")
    private String patientId;
    @SerializedName("name")
    private String name;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("email")
    private String email;

    @SerializedName("bloodgroup")
    private String bloodGroup;
    @SerializedName("dob")
    private String DoB;

    @SerializedName("photourl")
    private String photoUrl;
    @SerializedName("pin")
    private int pin;

    @SerializedName("address")
    private String address;

    @SerializedName("state")
    private String state;

    @SerializedName("city")
    private String city;

    @SerializedName("gender")
    private String gender;

    @SerializedName("maritalstatus")
    private String maritalStatus;

    @SerializedName("relationship")
    private String relationship;

    @SerializedName("bloodpressure")
    private String bloodPressure;

    @SerializedName("bmi")
    private int BMI;

    @SerializedName("height")
    private String height;

    @SerializedName("weight")
    private String weight;

    @SerializedName("whr")
    private String WHR;

    @SerializedName("age")
    private String age;

    public FamilyProfile(int id, String userId, String patientId, String name, String mobile, String email, String bloodGroup, String doB, String photoUrl, int pin, String address, String state, String city, String gender, String maritalStatus, String relationship, String bloodPressure, int BMI, String height, String weight, String WHR, String age) {
        this.id = id;
        this.userId = userId;
        this.patientId = patientId;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.bloodGroup = bloodGroup;
        DoB = doB;
        this.photoUrl = photoUrl;
        this.pin = pin;
        this.address = address;
        this.state = state;
        this.city = city;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.relationship = relationship;
        this.bloodPressure = bloodPressure;
        this.BMI = BMI;
        this.height = height;
        this.weight = weight;
        this.WHR = WHR;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getDoB() {
        return DoB;
    }

    public void setDoB(String doB) {
        DoB = doB;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public int getBMI() {
        return BMI;
    }

    public void setBMI(int BMI) {
        this.BMI = BMI;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWHR() {
        return WHR;
    }

    public void setWHR(String WHR) {
        this.WHR = WHR;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
