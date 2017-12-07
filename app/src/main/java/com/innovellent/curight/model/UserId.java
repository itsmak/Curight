package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pramod on 16-Nov-2017.
 */

public class UserId {
    @SerializedName("userid")
    private Long userid;
    private String date;

    public UserId(Long userid, String date) {
        this.userid = userid;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }
}
