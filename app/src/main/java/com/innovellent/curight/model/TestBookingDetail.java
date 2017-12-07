package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pramod on 16-Nov-2017.
 */

public class TestBookingDetail {
    @SerializedName("testbookingmasterid")
    private Long testbookingmasterid;

    public TestBookingDetail(Long testbookingmasterid) {
        this.testbookingmasterid = testbookingmasterid;
    }

    public Long getTestbookingmasterid() {
        return testbookingmasterid;
    }

    public void setTestbookingmasterid(Long testbookingmasterid) {
        this.testbookingmasterid = testbookingmasterid;
    }
}
