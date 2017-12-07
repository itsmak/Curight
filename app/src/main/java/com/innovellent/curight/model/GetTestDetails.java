package com.innovellent.curight.model;

/**
 * Created by SUNIL on 12/6/2017.
 */

public class GetTestDetails {

    private String testcode,testname,testknownas,department,description,testinstruction;

    public GetTestDetails(String testcode, String testname, String testknownas, String department, String description, String testinstruction) {

        this.testcode = testcode;
        this.testname = testname;
        this.testknownas = testknownas;
        this.department = department;
        this.description = description;
        this.testinstruction = testinstruction;
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

    public String getTestknownas() {
        return testknownas;
    }

    public void setTestknownas(String testknownas) {
        this.testknownas = testknownas;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTestinstruction() {
        return testinstruction;
    }

    public void setTestinstruction(String testinstruction) {
        this.testinstruction = testinstruction;
    }


}
