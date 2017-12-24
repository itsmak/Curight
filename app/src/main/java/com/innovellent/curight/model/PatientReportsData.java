package com.innovellent.curight.model;

/**
 * Created by SUNIL on 12/22/2017.
 */

public class PatientReportsData {

    private int patientreportid;
    private String visitdate,diagnosticcentreid,doctorid,reason,reportfilename,reportfiletype,comments,doctorname,doctornumber,diagnsticcentrename,visitday,visitmonth;

    public int getPatientreportid() {
        return patientreportid;
    }

    public void setPatientreportid(int patientreportid) {
        this.patientreportid = patientreportid;
    }

    public String getVisitdate() {
        return visitdate;
    }

    public void setVisitdate(String visitdate) {
        this.visitdate = visitdate;
    }

    public String getDiagnosticcentreid() {
        return diagnosticcentreid;
    }

    public void setDiagnosticcentreid(String diagnosticcentreid) {
        this.diagnosticcentreid = diagnosticcentreid;
    }

    public String getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(String doctorid) {
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

    public String getDiagnsticcentrename() {
        return diagnsticcentrename;
    }

    public void setDiagnsticcentrename(String diagnsticcentrename) {
        this.diagnsticcentrename = diagnsticcentrename;
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
