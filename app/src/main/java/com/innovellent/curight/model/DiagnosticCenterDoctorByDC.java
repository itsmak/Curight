package com.innovellent.curight.model;

/**
 * Created by SUNIL on 12/6/2017.
 */

public class DiagnosticCenterDoctorByDC {

    private Long diagnosticcentreid;
    private String testsid;

    public Long getDiagnosticcentreid() {
        return diagnosticcentreid;
    }

    public void setDiagnosticcentreid(Long diagnosticcentreid) {
        this.diagnosticcentreid = diagnosticcentreid;
    }

    public String getTestsid() {
        return testsid;
    }

    public void setTestsid(String testsid) {
        this.testsid = testsid;
    }

    public DiagnosticCenterDoctorByDC(Long diagnosticcentreid, String testsid) {

        this.diagnosticcentreid = diagnosticcentreid;
        this.testsid = testsid;
    }
}
