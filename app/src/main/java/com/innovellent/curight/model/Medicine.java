package com.innovellent.curight.model;

/**
 * Created by sagar on 9/19/2017.
 */

public class Medicine {
    private String medicinename;
    private String medicinemeasure;

    public Medicine(String medicinename,String medicinemeasure){
        this.medicinemeasure=medicinemeasure;
        this.medicinename=medicinename;
    }
    public String getMedicinename() {
        return medicinename;
    }

    public void setMedicinename(String medicinename) {
        this.medicinename = medicinename;
    }

    public String getMedicinemeasure() {
        return medicinemeasure;
    }

    public void setMedicinemeasure(String medicinemeasure) {
        this.medicinemeasure = medicinemeasure;
    }
}
