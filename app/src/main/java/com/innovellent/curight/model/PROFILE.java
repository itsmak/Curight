package com.innovellent.curight.model;

/**
 * Created by Mak on 12/5/2017.
 */

public class PROFILE {

    private String user_id;
    private String user_name;
    private String user_age;
    private String user_relation;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_age() {
        return user_age;
    }

    public void setUser_age(String user_age) {
        this.user_age = user_age;
    }

    public String getUser_relation() {
        return user_relation;
    }

    public void setUser_relation(String user_relation) {
        this.user_relation = user_relation;
    }

    public PROFILE(String user_id, String user_name, String user_age, String user_relation) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_age = user_age;
        this.user_relation = user_relation;
    }

}
