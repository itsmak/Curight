package com.innovellent.curight.model;

/**
 * Created by sagar on 9/13/2017.
 */

public class DiagnosticCentre {
    private Integer diagnosticcentreid;
    private String testsid;
    private String sortby;

    public DiagnosticCentre(Integer diagnosticcentreid, String testsid) {
        this.diagnosticcentreid = diagnosticcentreid;
        this.testsid = testsid;

    }

    public DiagnosticCentre(Integer diagnosticcentreid, String testsid, String sortby) {
        this.diagnosticcentreid = diagnosticcentreid;
        this.testsid = testsid;
        this.sortby = sortby;
    }

    public Integer getDiagnosticcentreid() {
        return diagnosticcentreid;
    }

    public void setDiagnosticcentreid(Integer diagnosticcentreid) {
        this.diagnosticcentreid = diagnosticcentreid;
    }

    public String getTestsid() {
        return testsid;
    }

    public void setTestsid(String testsid) {
        this.testsid = testsid;
    }

    public String getSortby() {
        return sortby;
    }

    public void setSortby(String sortby) {
        this.sortby = sortby;
    }
}