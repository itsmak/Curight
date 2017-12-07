package com.innovellent.curight.model;

import java.util.ArrayList;

/**
 * Created by Pramod on 31-10-2017.
 */

public class TestBookingCreate {
    private String patientname;
    private String patientphoneno;
    private String patientemail;
    private String referraldr;
    private String drcontactno;
    private String patientaddress;
    private String discountcouponcode;
    private String discount;
    private String diagnosticcentreid;
    private String dctestid;
    private String fullamount;
    private String homepickupdate;
    private String homepickuptime;
    private String labtestdate;
    private String userid;
    private String paymentackno;
    private String paymentdate;
    private String paymentmode;
    private String status;

    public TestBookingCreate(String patientname, String patientphoneno, String patientemail, String referraldr, String drcontactno, String patientaddress, String discountcouponcode, String discount, String diagnosticcentreid, String dctestid, String fullamount, String homepickupdate, String homepickuptime, String labtestdate, String userid, String paymentackno, String paymentdate, String paymentmode, String status) {
        this.patientname = patientname;
        this.patientphoneno = patientphoneno;
        this.patientemail = patientemail;
        this.referraldr = referraldr;
        this.drcontactno = drcontactno;
        this.patientaddress = patientaddress;
        this.discountcouponcode = discountcouponcode;
        this.discount = discount;
        this.diagnosticcentreid = diagnosticcentreid;
        this.dctestid = dctestid;
        this.fullamount = fullamount;
        this.homepickupdate = homepickupdate;
        this.homepickuptime = homepickuptime;
        this.labtestdate = labtestdate;
        this.userid = userid;
        this.paymentackno = paymentackno;
        this.paymentdate = paymentdate;
        this.paymentmode = paymentmode;
        this.status = status;
    }

    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }

    public String getPatientphoneno() {
        return patientphoneno;
    }

    public void setPatientphoneno(String patientphoneno) {
        this.patientphoneno = patientphoneno;
    }

    public String getPatientemail() {
        return patientemail;
    }

    public void setPatientemail(String patientemail) {
        this.patientemail = patientemail;
    }

    public String getReferraldr() {
        return referraldr;
    }

    public void setReferraldr(String referraldr) {
        this.referraldr = referraldr;
    }

    public String getDrcontactno() {
        return drcontactno;
    }

    public void setDrcontactno(String drcontactno) {
        this.drcontactno = drcontactno;
    }

    public String getPatientaddress() {
        return patientaddress;
    }

    public void setPatientaddress(String patientaddress) {
        this.patientaddress = patientaddress;
    }

    public String getDiscountcouponcode() {
        return discountcouponcode;
    }

    public void setDiscountcouponcode(String discountcouponcode) {
        this.discountcouponcode = discountcouponcode;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiagnosticcentreid() {
        return diagnosticcentreid;
    }

    public void setDiagnosticcentreid(String diagnosticcentreid) {
        this.diagnosticcentreid = diagnosticcentreid;
    }

    public String getDctestid() {
        return dctestid;
    }

    public void setDctestid(String dctestid) {
        this.dctestid = dctestid;
    }

    public String getFullamount() {
        return fullamount;
    }

    public void setFullamount(String fullamount) {
        this.fullamount = fullamount;
    }

    public String getHomepickupdate() {
        return homepickupdate;
    }

    public void setHomepickupdate(String homepickupdate) {
        this.homepickupdate = homepickupdate;
    }

    public String getHomepickuptime() {
        return homepickuptime;
    }

    public void setHomepickuptime(String homepickuptime) {
        this.homepickuptime = homepickuptime;
    }

    public String getLabtestdate() {
        return labtestdate;
    }

    public void setLabtestdate(String labtestdate) {
        this.labtestdate = labtestdate;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPaymentackno() {
        return paymentackno;
    }

    public void setPaymentackno(String paymentackno) {
        this.paymentackno = paymentackno;
    }

    public String getPaymentdate() {
        return paymentdate;
    }

    public void setPaymentdate(String paymentdate) {
        this.paymentdate = paymentdate;
    }

    public String getPaymentmode() {
        return paymentmode;
    }

    public void setPaymentmode(String paymentmode) {
        this.paymentmode = paymentmode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
