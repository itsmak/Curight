package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pramod on 16-Nov-2017.
 */

public class BookedTestsArr {
    @SerializedName("testbookingchildid")
    private Long testbookingchildid;
    private Long testbookingmasterid;
    private Long diagnostictestid;
    private Long modifiedby;
    private String name;
    private Long amount;

    public BookedTestsArr(Long testbookingchildid, Long testbookingmasterid, Long diagnostictestid, Long modifiedby, String name, Long amount) {
        this.testbookingchildid = testbookingchildid;
        this.testbookingmasterid = testbookingmasterid;
        this.diagnostictestid = diagnostictestid;
        this.modifiedby = modifiedby;
        this.name = name;
        this.amount = amount;
    }

    public Long getTestbookingchildid() {
        return testbookingchildid;
    }

    public void setTestbookingchildid(Long testbookingchildid) {
        this.testbookingchildid = testbookingchildid;
    }

    public Long getTestbookingmasterid() {
        return testbookingmasterid;
    }

    public void setTestbookingmasterid(Long testbookingmasterid) {
        this.testbookingmasterid = testbookingmasterid;
    }

    public Long getDiagnostictestid() {
        return diagnostictestid;
    }

    public void setDiagnostictestid(Long diagnostictestid) {
        this.diagnostictestid = diagnostictestid;
    }

    public Long getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(Long modifiedby) {
        this.modifiedby = modifiedby;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
