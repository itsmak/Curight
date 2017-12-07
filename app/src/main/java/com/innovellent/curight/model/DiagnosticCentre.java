package com.innovellent.curight.model;

/**
 * Created by sagar on 9/13/2017.
 */

public class DiagnosticCentre {
    private Integer diagnosticcentreid;
    private String testsid;

    public DiagnosticCentre(Integer diagnosticcentreid, String testsid) {
        this.diagnosticcentreid = diagnosticcentreid;
        this.testsid = testsid;
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
}
