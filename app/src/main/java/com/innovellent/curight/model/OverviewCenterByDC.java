package com.innovellent.curight.model;

/**
 * Created by SUNIL on 12/7/2017.
 */

public class OverviewCenterByDC {

    private Long diagnosticcentreid;

    public Long getDiagnosticcentreid() {
        return diagnosticcentreid;
    }

    public void setDiagnosticcentreid(Long diagnosticcentreid) {
        this.diagnosticcentreid = diagnosticcentreid;
    }

    public OverviewCenterByDC(Long diagnosticcentreid) {

        this.diagnosticcentreid = diagnosticcentreid;
    }
}
