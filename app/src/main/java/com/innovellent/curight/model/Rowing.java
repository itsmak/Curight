package com.innovellent.curight.model;

/**
 * Created by Mak on 3/25/2018.
 */

public class Rowing {
    private Long exerciseid;
    private Long speed;
    private Long distancecovered;
    private Long caloriesburnt;
    private String exercisetype;
    private String exercisedate;
    private String exerciseat;
    private String exercisetime;

    public Rowing(Long exerciseid, Long speed, Long distancecovered, Long caloriesburnt, String exercisetype, String exercisedate, String exerciseat, String exercisetime) {
        this.exerciseid = exerciseid;
        this.speed = speed;
        this.distancecovered = distancecovered;
        this.caloriesburnt = caloriesburnt;
        this.exercisetype = exercisetype;
        this.exercisedate = exercisedate;
        this.exerciseat = exerciseat;
        this.exercisetime = exercisetime;
    }

    public Long getExerciseid() {
        return exerciseid;
    }

    public void setExerciseid(Long exerciseid) {
        this.exerciseid = exerciseid;
    }

    public Long getSpeed() {
        return speed;
    }

    public void setSpeed(Long speed) {
        this.speed = speed;
    }

    public Long getDistancecovered() {
        return distancecovered;
    }

    public void setDistancecovered(Long distancecovered) {
        this.distancecovered = distancecovered;
    }

    public Long getCaloriesburnt() {
        return caloriesburnt;
    }

    public void setCaloriesburnt(Long caloriesburnt) {
        this.caloriesburnt = caloriesburnt;
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

    public String getExerciseat() {
        return exerciseat;
    }

    public void setExerciseat(String exerciseat) {
        this.exerciseat = exerciseat;
    }

    public String getExercisetime() {
        return exercisetime;
    }

    public void setExercisetime(String exercisetime) {
        this.exercisetime = exercisetime;
    }
}
