package com.innovellent.curight.model;

/**
 * Created by Pramod on 9/13/2017.
 */

public class Auth {
    private String userid;
    private String password;
    private String loginFrom;


    public Auth(String userid, String password, String loginFrom){
        this.userid = userid;
        this.password = password;
        this.loginFrom = loginFrom;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginFrom() {
        return loginFrom;
    }

    public void setLoginFrom(String loginFrom) {
        this.loginFrom = loginFrom;
    }
}
