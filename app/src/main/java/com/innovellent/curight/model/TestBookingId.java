package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pramod on 16-Nov-2017.
 */

public class TestBookingId {
    @SerializedName("testbookingid")
    private Long testbookingid;

    public TestBookingId(Long testbookingid) {
        this.testbookingid = testbookingid;
    }

    public Long getTestbookingid() {
        return testbookingid;
    }

    public void setTestbookingid(Long testbookingid) {
        this.testbookingid = testbookingid;
    }
}
