package com.innovellent.curight.model;

/**
 * Created by sagar on 8/31/2017.
 */

public class BloodPressure {
    private String systolic;
    private String pulse;
    private String time;
    private String date;


    public   BloodPressure(String systolic,String pulse){
        this.systolic=systolic;
        this.pulse=pulse;
    }
    public String getSystolic() {
        return systolic;
    }

    public void setSystolic(String systolic) {
        this.systolic = systolic;
    }

    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
