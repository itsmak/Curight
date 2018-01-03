package com.innovellent.curight.model;

/**
 * Created by SUNIL on 12/30/2017.
 */

public class BP {

    private int pulse,bpid,systolic,diastolic;
    private String bpFlag;
    private String date;
    private String time;



    private String graphflag;

    public BP(String date,int bpid,String time,String graphflag,int pulse,int systolic, int diastolic, String bpFlag) {
        this.pulse = pulse;
        this.bpid = bpid;
        this.systolic = systolic;
        this.graphflag = graphflag;
        this.diastolic = diastolic;
        this.bpFlag = bpFlag;
        this.date = date;
        this.time = time;
    }

    public int getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public int getBpid() {
        return bpid;
    }

    public void setBpid(int bpid) {
        this.bpid = bpid;
    }

    public int getSystolic() {
        return systolic;
    }

    public void setSystolic(int systolic) {
        this.systolic = systolic;
    }

    public int getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(int diastolic) {
        this.diastolic = diastolic;
    }

    public String getBpFlag() {
        return bpFlag;
    }

    public void setBpFlag(String bpFlag) {
        this.bpFlag = bpFlag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGraphflag() {
        return graphflag;
    }

    public void setGraphflag(String graphflag) {
        this.graphflag = graphflag;
    }
}
