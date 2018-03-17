package com.innovellent.curight.model;

/**
 * Created by Mak on 3/15/2018.
 */

public class SelectedTest {

    private Long testid;
    private String testname;

    public SelectedTest(Long testid, String testname) {
        this.testid = testid;
        this.testname = testname;
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
}
