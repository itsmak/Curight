package com.innovellent.curight.model;

/**
 * Created by sagar on 9/8/2017.
 */

public class PaymentDetails {
    private String testname;
    private String amount;

    public PaymentDetails(String testname,String amount){
        this.testname=testname;
        this.amount=amount;

    }
    public String getTestname() {
        return testname;
    }

    public void setTestname(String testname) {
        this.testname = testname;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
