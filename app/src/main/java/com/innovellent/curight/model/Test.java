package com.innovellent.curight.model;

import org.json.JSONObject;

import java.lang.String;

/**
 * Created by Pramod on 31-10-2017.
 */

public class Test {
    private String testname;
    private Long testid;
    private Long diagnosticcentreid;
    private String testcode;
    private String description;
    private Long modifiedby;

    public Test(String testname,Long testid,Long diagnosticcentreid,String testcode,String description,Long modifiedby){
        this.testname = testname;
        this.testid = testid;
        this.diagnosticcentreid = diagnosticcentreid;
        this.testcode = testcode;
        this.description = description;
        this.modifiedby = modifiedby;

    }

    public Long getTestid() {
        return testid;
    }

    public void setTestid(Long testid) {
        this.testid = testid;
    }

    public String getTestname() {
        return testname;
    }

    public void setTestname(String testname) {
        this.testname = testname;
    }

    public Long getDiagnosticcentreid() {
        return diagnosticcentreid;
    }

    public void setDiagnosticcentreid(Long diagnosticcentreid) {
        this.diagnosticcentreid = diagnosticcentreid;
    }

    public String getTestcode() {
        return testcode;
    }

    public void setTestcode(String testcode) {
        this.testcode = testcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(Long modifiedby) {
        this.modifiedby = modifiedby;
    }
}
