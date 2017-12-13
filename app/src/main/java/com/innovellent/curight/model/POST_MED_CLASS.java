package com.innovellent.curight.model;

/**
 * Created by Mak on 12/12/2017.
 */

public class POST_MED_CLASS {

    private int userid;
    private String medicinetakendate;

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getMedicinetakendate() {
        return medicinetakendate;
    }

    public void setMedicinetakendate(String medicinetakendate) {
        this.medicinetakendate = medicinetakendate;
    }

    public POST_MED_CLASS(int userid, String medicinetakendate) {
        this.userid = userid;
        this.medicinetakendate = medicinetakendate;
    }
}
