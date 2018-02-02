package com.innovellent.curight.model;

/**
 * Created by Mak on 1/31/2018.
 */

public class PostBodyAddExersize {


    private String userid;
    private String exercisetype;
    private String exercisedate;
    private String exercisetime;
    private String exerciseat;
    private String speed;
    private String distancecovered;
    private String caloriesburnt;

    public PostBodyAddExersize(String userid, String exercisetype, String exercisedate, String exercisetime, String exerciseat, String speed, String distancecovered, String caloriesburnt) {
        this.userid = userid;
        this.exercisetype = exercisetype;
        this.exercisedate = exercisedate;
        this.exercisetime = exercisetime;
        this.exerciseat = exerciseat;
        this.speed = speed;
        this.distancecovered = distancecovered;
        this.caloriesburnt = caloriesburnt;
    }
}
