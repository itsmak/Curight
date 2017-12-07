package com.innovellent.curight.model;

/**
 * Created by sagar on 9/8/2017.
 */

public class Swimming {
    private String name;
    private String hour;
    private String distance;

    public Swimming(String name,String hour,String distance){
        this.name=name;
        this.hour=hour;
        this.distance=distance;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
