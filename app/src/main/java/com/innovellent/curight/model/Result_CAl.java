package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mak on 1/31/2018.
 */

public class Result_CAl {
    @SerializedName("userid")
    private Integer userid;
    @SerializedName("activity")
    private String activity;
    @SerializedName("duration")
    private String duration;
    @SerializedName("calories")
    private Integer calories;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }
}
