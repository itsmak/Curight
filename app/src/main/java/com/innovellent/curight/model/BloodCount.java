package com.innovellent.curight.model;

/**
 * Created by sagar on 10/1/2017.
 */

public class BloodCount {
    private String  AntiCPP;
    private String  CRP;
    private String ESR;
    private String Haemoglobin;
    private String HbA1c;
    private String INR;
    private String Platelet;
    private String Prolactin;
    private String RBC;
    private String RF;
    private String WBC;

    public  BloodCount(String AntiCPP,String CRP,String ESR,String Haemoglobin,String HbA1c,String INR,String Platelet,String Prolactin,String RBC,String RF,String WBC){
        this.AntiCPP=AntiCPP;
        this.CRP=CRP;
        this.ESR=ESR;
        this.Haemoglobin=Haemoglobin;
        this.HbA1c=HbA1c;
        this.INR=INR;
        this.Platelet=Platelet;
        this.Prolactin=Prolactin;
        this.RBC=RBC;
        this.RF=RF;
        this.WBC=WBC;


    }

    public String getAntiCPP() {
        return AntiCPP;
    }

    public void setAntiCPP(String antiCPP) {
        AntiCPP = antiCPP;
    }

    public String getCRP() {
        return CRP;
    }

    public void setCRP(String CRP) {
        this.CRP = CRP;
    }

    public String getESR() {
        return ESR;
    }

    public void setESR(String ESR) {
        this.ESR = ESR;
    }

    public String getHaemoglobin() {
        return Haemoglobin;
    }

    public void setHaemoglobin(String haemoglobin) {
        Haemoglobin = haemoglobin;
    }

    public String getHbA1c() {
        return HbA1c;
    }

    public void setHbA1c(String hbA1c) {
        HbA1c = hbA1c;
    }

    public String getINR() {
        return INR;
    }

    public void setINR(String INR) {
        this.INR = INR;
    }

    public String getPlatelet() {
        return Platelet;
    }

    public void setPlatelet(String platelet) {
        Platelet = platelet;
    }

    public String getProlactin() {
        return Prolactin;
    }

    public void setProlactin(String prolactin) {
        Prolactin = prolactin;
    }

    public String getRBC() {
        return RBC;
    }

    public void setRBC(String RBC) {
        this.RBC = RBC;
    }

    public String getRF() {
        return RF;
    }

    public void setRF(String RF) {
        this.RF = RF;
    }

    public String getWBC() {
        return WBC;
    }

    public void setWBC(String WBC) {
        this.WBC = WBC;
    }
}
