package com.innovellent.curight.fragment;

/**
 * Created by Mak on 12/21/2017.
 */

public class Medicine_list {

    private int medicineid;
    private String medicinename;

    public Medicine_list(int medicineid, String medicinename) {
        this.medicineid = medicineid;
        this.medicinename = medicinename;
    }

    public int getMedicineid() {
        return medicineid;
    }

    public void setMedicineid(int medicineid) {
        this.medicineid = medicineid;
    }

    public String getMedicinename() {
        return medicinename;
    }

    public void setMedicinename(String medicinename) {
        this.medicinename = medicinename;
    }
}
