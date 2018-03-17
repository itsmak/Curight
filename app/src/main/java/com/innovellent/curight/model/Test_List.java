package com.innovellent.curight.model;

/**
 * Created by Mak on 12/27/2017.
 */

public class Test_List {

    private Long testid;
    private String testcode;
    private String testname;
    private String description;
    private Long modifiedby;
    private boolean checked;
  //  private boolean isSelected;


    public Test_List(Long testid, String testcode, String testname, String description, Long modifiedby, boolean checked) {
        this.testid = testid;
        this.testcode = testcode;
        this.testname = testname;
        this.description = description;
        this.modifiedby = modifiedby;
        this.checked = checked;

    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Long getTestid() {
        return testid;
    }

    public void setTestid(Long testid) {
        this.testid = testid;
    }

    public String getTestcode() {
        return testcode;
    }

    public void setTestcode(String testcode) {
        this.testcode = testcode;
    }

    public String getTestname() {
        return testname;
    }

    public void setTestname(String testname) {
        this.testname = testname;
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
