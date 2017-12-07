package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Pramod on 16-Nov-2017.
 */

public class Exercise {
    @SerializedName("totalcaloriesburnt")
    private Long totalcaloriesburnt;
    private ArrayList<Running> Running;
    private ArrayList<Walking> Walking;
    private ArrayList<Swimming> Swimming;
    private ArrayList<Bicycling> Bicycling;

    public Exercise(Long totalcaloriesburnt, ArrayList<com.innovellent.curight.model.Running> running, ArrayList<com.innovellent.curight.model.Walking> walking, ArrayList<com.innovellent.curight.model.Swimming> swimming, ArrayList<com.innovellent.curight.model.Bicycling> bicycling) {
        this.totalcaloriesburnt = totalcaloriesburnt;
        Running = running;
        Walking = walking;
        Swimming = swimming;
        Bicycling = bicycling;
    }

    public Long getTotalcaloriesburnt() {
        return totalcaloriesburnt;
    }

    public void setTotalcaloriesburnt(Long totalcaloriesburnt) {
        this.totalcaloriesburnt = totalcaloriesburnt;
    }

    public ArrayList<com.innovellent.curight.model.Running> getRunning() {
        return Running;
    }

    public void setRunning(ArrayList<com.innovellent.curight.model.Running> running) {
        Running = running;
    }

    public ArrayList<com.innovellent.curight.model.Walking> getWalking() {
        return Walking;
    }

    public void setWalking(ArrayList<com.innovellent.curight.model.Walking> walking) {
        Walking = walking;
    }

    public ArrayList<com.innovellent.curight.model.Swimming> getSwimming() {
        return Swimming;
    }

    public void setSwimming(ArrayList<com.innovellent.curight.model.Swimming> swimming) {
        Swimming = swimming;
    }

    public ArrayList<com.innovellent.curight.model.Bicycling> getBicycling() {
        return Bicycling;
    }

    public void setBicycling(ArrayList<com.innovellent.curight.model.Bicycling> bicycling) {
        Bicycling = bicycling;
    }
}
