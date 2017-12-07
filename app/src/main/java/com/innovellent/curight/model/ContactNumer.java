package com.innovellent.curight.model;

/**
 * Created by sagar on 9/12/2017.
 */

public class ContactNumer {
    private String name;
    private String number;


    public void ContactNumer(String name,String number ){
        this.name=name;
        this.number=number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
