package com.innovellent.curight.model;

/**
 * Created by Mak on 2/2/2018.
 */

public class SummaryDetails {

    private String name;
    private String amount;
    private String homepickup;


    public SummaryDetails(String name, String amount, String homepickup) {
        this.name = name;
        this.amount = amount;
        this.homepickup = homepickup;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getHomepickup() {
        return homepickup;
    }

    public void setHomepickup(String homepickup) {
        this.homepickup = homepickup;
    }


}
