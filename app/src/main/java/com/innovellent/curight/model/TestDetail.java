package com.innovellent.curight.model;

/**
 * Created by Pramod on 31-10-2017.
 */

public class TestDetail {
    private Long diagnostictestId;
    private Long testid;
    private String testName;
    private Long amount;
    private String homePickupFlag;
    private String labPickupFlag;
    private String testchoosen;

    public TestDetail(Long diagnostictestId, Long testid, String testName, Long amount, String homePickupFlag, String labPickupFlag, String testchoosen) {
        this.diagnostictestId = diagnostictestId;
        this.testid = testid;
        this.testName = testName;
        this.amount = amount;
        this.homePickupFlag = homePickupFlag;
        this.labPickupFlag = labPickupFlag;
        this.testchoosen = testchoosen;
    }

    public Long getDiagnostictestId() {
        return diagnostictestId;
    }

    public void setDiagnostictestId(Long diagnostictestId) {
        this.diagnostictestId = diagnostictestId;
    }

    public Long getTestid() {
        return testid;
    }

    public void setTestid(Long testid) {
        this.testid = testid;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getHomePickupFlag() {
        return homePickupFlag;
    }

    public void setHomePickupFlag(String homePickupFlag) {
        this.homePickupFlag = homePickupFlag;
    }

    public String getLabPickupFlag() {
        return labPickupFlag;
    }

    public void setLabPickupFlag(String labPickupFlag) {
        this.labPickupFlag = labPickupFlag;
    }

    public String getTestchoosen() {
        return testchoosen;
    }

    public void setTestchoosen(String testchoosen) {
        this.testchoosen = testchoosen;
    }
}
