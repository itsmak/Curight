package com.innovellent.curight.model;

/**
 * Created by SUNIL on 12/6/2017.
 */

public class GetTestDetailCenter {

    private Long diagnosticcentreid;

    public Long getDiagnosticcentreid() {
        return diagnosticcentreid;
    }

    public void setDiagnosticcentreid(Long diagnosticcentreid) {
        this.diagnosticcentreid = diagnosticcentreid;
    }

    public GetTestDetailCenter(Long diagnosticcentreid) {

        this.diagnosticcentreid = diagnosticcentreid;
    }
}
