package com.innovellent.curight.model;

/**
 * Created by Pramod on 9/13/2017.
 */

public class Login {
    private String userid;
    private String password;
    private String loginfrom;

    public Login(String userid, String password,String loginfrom){
        this.userid = userid;
        this.password = password;
        this.loginfrom = loginfrom;
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

    public String getLoginfrom() {
        return loginfrom;
    }

    public void setLoginfrom(String loginfrom) {
        this.loginfrom = loginfrom;
    }
}
