package com.innovellent.curight.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mak on 2/5/2018.
 */

public class Report_FEED {

    @SerializedName("patientreportid")
    @Expose
    int patientreportid;

    @SerializedName("userid")
    @Expose
    int userid;

    @SerializedName("visitdate")
    @Expose
    String visitdate;

    @SerializedName("diagnosticcentreid")
    @Expose
    int diagnosticcentreid;

    @SerializedName("doctorid")
    @Expose
    int doctorid;

    @SerializedName("reason")
    @Expose
    String reason;

    @SerializedName("reportfilename")
    @Expose
    String reportfilename;

    @SerializedName("reportfiletype")
    @Expose
    String reportfiletype;

    @SerializedName("comments")
    @Expose
    String comments;

    @SerializedName("doctorname")
    @Expose
    String doctorname;

    @SerializedName("doctornumber")
    @Expose
    String doctornumber;

    @SerializedName("diagnosticcentrename")
    @Expose
    String diagnosticcentrename;

    @SerializedName("visitday")
    @Expose
    String visitday;

    @SerializedName("visitmonth")
    @Expose
    String visitmonth;

    public int getPatientreportid() {
        return patientreportid;
    }

    public void setPatientreportid(int patientreportid) {
        this.patientreportid = patientreportid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getVisitdate() {
        return visitdate;
    }

    public void setVisitdate(String visitdate) {
        this.visitdate = visitdate;
    }

    public int getDiagnosticcentreid() {
        return diagnosticcentreid;
    }

    public void setDiagnosticcentreid(int diagnosticcentreid) {
        this.diagnosticcentreid = diagnosticcentreid;
    }

    public int getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(int doctorid) {
        this.doctorid = doctorid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReportfilename() {
        return reportfilename;
    }

    public void setReportfilename(String reportfilename) {
        this.reportfilename = reportfilename;
    }

    public String getReportfiletype() {
        return reportfiletype;
    }

    public void setReportfiletype(String reportfiletype) {
        this.reportfiletype = reportfiletype;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public String getDoctornumber() {
        return doctornumber;
    }

    public void setDoctornumber(String doctornumber) {
        this.doctornumber = doctornumber;
    }

    public String getDiagnosticcentrename() {
        return diagnosticcentrename;
    }

    public void setDiagnosticcentrename(String diagnosticcentrename) {
        this.diagnosticcentrename = diagnosticcentrename;
    }

    public String getVisitday() {
        return visitday;
    }

    public void setVisitday(String visitday) {
        this.visitday = visitday;
    }

    public String getVisitmonth() {
        return visitmonth;
    }

    public void setVisitmonth(String visitmonth) {
        this.visitmonth = visitmonth;
    }
}
