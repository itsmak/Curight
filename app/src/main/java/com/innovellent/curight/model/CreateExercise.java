package com.innovellent.curight.model;

/**
 * Created by Pramod on 9/13/2017.
 */

public class CreateExercise {
    private String userid;
    private String exercisetype;
    private String exercisedate;
    private String exercisetime;
    private String exerciseat;
    private String speed;
    private String distancecovered;
    private String caloriesburnt;


    public CreateExercise(String userid, String exercisetype, String exercisedate, String exercisetime, String exerciseat, String speed, String distancecovered, String caloriesburnt) {
        this.userid = userid;
        this.exercisetype = exercisetype;
        this.exercisedate = exercisedate;
        this.exercisetime = exercisetime;
        this.exerciseat = exerciseat;
        this.speed = speed;
        this.distancecovered = distancecovered;
        this.caloriesburnt = caloriesburnt;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getExercisetype() {
        return exercisetype;
    }

    public void setExercisetype(String exercisetype) {
        this.exercisetype = exercisetype;
    }

    public String getExercisedate() {
        return exercisedate;
    }

    public void setExercisedate(String exercisedate) {
        this.exercisedate = exercisedate;
    }

    public String getExercisetime() {
        return exercisetime;
    }

    public void setExercisetime(String exercisetime) {
        this.exercisetime = exercisetime;
    }

    public String getExerciseat() {
        return exerciseat;
    }

    public void setExerciseat(String exerciseat) {
        this.exerciseat = exerciseat;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getDistancecovered() {
        return distancecovered;
    }

    public void setDistancecovered(String distancecovered) {
        this.distancecovered = distancecovered;
    }

    public String getCaloriesburnt() {
        return caloriesburnt;
    }

    public void setCaloriesburnt(String caloriesburnt) {
        this.caloriesburnt = caloriesburnt;
    }
}
