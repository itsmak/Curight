package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Pramod on 08-Nov-2017.
 */

public class BookedTest {
    private Long testbookingmasterid;
    private Long userid;
    private String fullamount;
    private Long diagnosticcentreid;
    private String homepickupdate;
    private String homepickuptime;
    private String labtestdate;
    private String paymentmode;
    private String discountcouponcode;
    private Long discount;
    private String paymentackno;
    private String paymentdate;
    private Long modifiedby;
    private String patientname;
    private String patientphoneno;
    private String patientemail;
    private String referraldr;
    private String drcontactno;
    private String patientaddress;
    private ArrayList<BookedTestsArr> tests;

    public BookedTest(Long testbookingmasterid, Long userid, String fullamount, Long diagnosticcentreid, String homepickupdate, String homepickuptime, String labtestdate, String paymentmode, String discountcouponcode, Long discount, String paymentackno, String paymentdate, Long modifiedby, String patientname, String patientphoneno, String patientemail, String referraldr, String drcontactno, String patientaddress, ArrayList<BookedTestsArr> tests) {
        this.testbookingmasterid = testbookingmasterid;
        this.userid = userid;
        this.fullamount = fullamount;
        this.diagnosticcentreid = diagnosticcentreid;
        this.homepickupdate = homepickupdate;
        this.homepickuptime = homepickuptime;
        this.labtestdate = labtestdate;
        this.paymentmode = paymentmode;
        this.discountcouponcode = discountcouponcode;
        this.discount = discount;
        this.paymentackno = paymentackno;
        this.paymentdate = paymentdate;
        this.modifiedby = modifiedby;
        this.patientname = patientname;
        this.patientphoneno = patientphoneno;
        this.patientemail = patientemail;
        this.referraldr = referraldr;
        this.drcontactno = drcontactno;
        this.patientaddress = patientaddress;
        this.tests = tests;
    }

    public Long getTestbookingmasterid() {
        return testbookingmasterid;
    }

    public void setTestbookingmasterid(Long testbookingmasterid) {
        this.testbookingmasterid = testbookingmasterid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getFullamount() {
        return fullamount;
    }

    public void setFullamount(String fullamount) {
        this.fullamount = fullamount;
    }

    public Long getDiagnosticcentreid() {
        return diagnosticcentreid;
    }

    public void setDiagnosticcentreid(Long diagnosticcentreid) {
        this.diagnosticcentreid = diagnosticcentreid;
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

    public String getPaymentmode() {
        return paymentmode;
    }

    public void setPaymentmode(String paymentmode) {
        this.paymentmode = paymentmode;
    }

    public String getDiscountcouponcode() {
        return discountcouponcode;
    }

    public void setDiscountcouponcode(String discountcouponcode) {
        this.discountcouponcode = discountcouponcode;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
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

    public Long getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(Long modifiedby) {
        this.modifiedby = modifiedby;
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

    public ArrayList<BookedTestsArr> getTests() {
        return tests;
    }

    public void setTests(ArrayList<BookedTestsArr> tests) {
        this.tests = tests;
    }
}
