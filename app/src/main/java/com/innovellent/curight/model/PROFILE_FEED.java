package com.innovellent.curight.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mak on 12/5/2017.
 */

public class PROFILE_FEED {

    @SerializedName("userid")
    @Expose
    String userid;

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("age")
    @Expose
    String age;

    @SerializedName("relationship")
    @Expose
    String relationship;

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
