package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mak on 5/20/2018.
 */

public class CouponRes {

    @SerializedName("copounid")
    private int copounid;

    @SerializedName("userid")
    private int userid;

    @SerializedName("copouncode")
    private String copouncode;

    @SerializedName("purpose")
    private String purpose;

    @SerializedName("activefrom")
    private String activefrom;

    @SerializedName("activeto")
    private String activeto;

    @SerializedName("type")
    private String type;

    @SerializedName("status")
    private String status;

    @SerializedName("discount")
    private String discount;

    public int getCopounid() {
        return copounid;
    }

    public void setCopounid(int copounid) {
        this.copounid = copounid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getCopouncode() {
        return copouncode;
    }

    public void setCopouncode(String copouncode) {
        this.copouncode = copouncode;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getActivefrom() {
        return activefrom;
    }

    public void setActivefrom(String activefrom) {
        this.activefrom = activefrom;
    }

    public String getActiveto() {
        return activeto;
    }

    public void setActiveto(String activeto) {
        this.activeto = activeto;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
