package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mak on 12/21/2017.
 */

public class Medicine_List_Feed {

    @SerializedName("medicineid")
    private int medicineid;

    @SerializedName("medicinename")
    private String medicinename;

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
