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
    private ArrayList<Golf> Golf;
    private ArrayList<Frisbee> Frisbee;
    private ArrayList<Racquetball> Racquetball;
    private ArrayList<Rowing> Rowing;
    private ArrayList<Soccer> Soccer;
    private ArrayList<Softball> Softball;
    private ArrayList<Tennis> Tennis;
    private ArrayList<TrailBiking> TrailBiking;
    private ArrayList<Volleyball> Volleyball;
    private ArrayList<Weightlifting> Weightlifting;
    private ArrayList<Wrestling> Wrestling;
    private ArrayList<Yoga> Yoga;

    public Exercise(Long totalcaloriesburnt, ArrayList<com.innovellent.curight.model.Running> running, ArrayList<com.innovellent.curight.model.Walking> walking, ArrayList<com.innovellent.curight.model.Swimming> swimming, ArrayList<com.innovellent.curight.model.Bicycling> bicycling, ArrayList<com.innovellent.curight.model.Golf> golf, ArrayList<com.innovellent.curight.model.Frisbee> frisbee, ArrayList<com.innovellent.curight.model.Racquetball> racquetball, ArrayList<com.innovellent.curight.model.Rowing> rowing, ArrayList<com.innovellent.curight.model.Soccer> soccer, ArrayList<com.innovellent.curight.model.Softball> softball, ArrayList<com.innovellent.curight.model.Tennis> tennis, ArrayList<com.innovellent.curight.model.TrailBiking> trailBiking, ArrayList<com.innovellent.curight.model.Volleyball> volleyball, ArrayList<com.innovellent.curight.model.Weightlifting> weightlifting, ArrayList<com.innovellent.curight.model.Wrestling> wrestling, ArrayList<com.innovellent.curight.model.Yoga> yoga) {
        this.totalcaloriesburnt = totalcaloriesburnt;
        Running = running;
        Walking = walking;
        Swimming = swimming;
        Bicycling = bicycling;
        Golf = golf;
        Frisbee = frisbee;
        Racquetball = racquetball;
        Rowing = rowing;
        Soccer = soccer;
        Softball = softball;
        Tennis = tennis;
        TrailBiking = trailBiking;
        Volleyball = volleyball;
        Weightlifting = weightlifting;
        Wrestling = wrestling;
        Yoga = yoga;
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

    public ArrayList<com.innovellent.curight.model.Golf> getGolf() {
        return Golf;
    }

    public void setGolf(ArrayList<com.innovellent.curight.model.Golf> golf) {
        Golf = golf;
    }

    public ArrayList<com.innovellent.curight.model.Frisbee> getFrisbee() {
        return Frisbee;
    }

    public void setFrisbee(ArrayList<com.innovellent.curight.model.Frisbee> frisbee) {
        Frisbee = frisbee;
    }

    public ArrayList<com.innovellent.curight.model.Racquetball> getRacquetball() {
        return Racquetball;
    }

    public void setRacquetball(ArrayList<com.innovellent.curight.model.Racquetball> racquetball) {
        Racquetball = racquetball;
    }

    public ArrayList<com.innovellent.curight.model.Rowing> getRowing() {
        return Rowing;
    }

    public void setRowing(ArrayList<com.innovellent.curight.model.Rowing> rowing) {
        Rowing = rowing;
    }

    public ArrayList<com.innovellent.curight.model.Soccer> getSoccer() {
        return Soccer;
    }

    public void setSoccer(ArrayList<com.innovellent.curight.model.Soccer> soccer) {
        Soccer = soccer;
    }

    public ArrayList<com.innovellent.curight.model.Softball> getSoftball() {
        return Softball;
    }

    public void setSoftball(ArrayList<com.innovellent.curight.model.Softball> softball) {
        Softball = softball;
    }

    public ArrayList<com.innovellent.curight.model.Tennis> getTennis() {
        return Tennis;
    }

    public void setTennis(ArrayList<com.innovellent.curight.model.Tennis> tennis) {
        Tennis = tennis;
    }

    public ArrayList<com.innovellent.curight.model.TrailBiking> getTrailBiking() {
        return TrailBiking;
    }

    public void setTrailBiking(ArrayList<com.innovellent.curight.model.TrailBiking> trailBiking) {
        TrailBiking = trailBiking;
    }

    public ArrayList<com.innovellent.curight.model.Volleyball> getVolleyball() {
        return Volleyball;
    }

    public void setVolleyball(ArrayList<com.innovellent.curight.model.Volleyball> volleyball) {
        Volleyball = volleyball;
    }

    public ArrayList<com.innovellent.curight.model.Weightlifting> getWeightlifting() {
        return Weightlifting;
    }

    public void setWeightlifting(ArrayList<com.innovellent.curight.model.Weightlifting> weightlifting) {
        Weightlifting = weightlifting;
    }

    public ArrayList<com.innovellent.curight.model.Wrestling> getWrestling() {
        return Wrestling;
    }

    public void setWrestling(ArrayList<com.innovellent.curight.model.Wrestling> wrestling) {
        Wrestling = wrestling;
    }

    public ArrayList<com.innovellent.curight.model.Yoga> getYoga() {
        return Yoga;
    }

    public void setYoga(ArrayList<com.innovellent.curight.model.Yoga> yoga) {
        Yoga = yoga;
    }
}
