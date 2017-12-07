package com.innovellent.curight.model;

/**
 * Created by Mak on 12/7/2017.
 */

public class POST_UPDATE_CLASS {

    private int userid;
    private int vaccineactivityid;
    private String vaccinedate;
    private String comments;
    private String doctorname;
    private String reminder1;
    private String reminder2;

    public POST_UPDATE_CLASS(int userid, int vaccineactivityid, String vaccinedate, String comments, String doctorname, String reminder1, String reminder2) {
        this.userid = userid;
        this.vaccineactivityid = vaccineactivityid;
        this.vaccinedate = vaccinedate;
        this.comments = comments;
        this.doctorname = doctorname;
        this.reminder1 = reminder1;
        this.reminder2 = reminder2;
    }
}
