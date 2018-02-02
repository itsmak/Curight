package com.innovellent.curight.model;

/**
 * Created by SUNIL on 12/7/2017.
 */

public class GetOverviewList {

    private String summary;
    private String diagnosticcentrename;
    public GetOverviewList(String summary,String diagnosticcentrename) {

        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDiagnosticcentrename() {
        return diagnosticcentrename;
    }

    public void setDiagnosticcentrename(String diagnosticcentrename) {
        this.diagnosticcentrename = diagnosticcentrename;
    }
}
