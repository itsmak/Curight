package com.innovellent.curight.model;

/**
 * Created by Mak on 3/22/2018.
 */

public class FoodConsumptionModel {

    private String userid;
    private String date;
    private String foodid;
    private String mealtypename;
    private String serving_qty;
    private String serving_unit;
    private String time;

    public FoodConsumptionModel(String userid, String date, String foodid, String mealtypename, String serving_qty, String serving_unit, String time) {
        this.userid = userid;
        this.date = date;
        this.foodid = foodid;
        this.mealtypename = mealtypename;
        this.serving_qty = serving_qty;
        this.serving_unit = serving_unit;
        this.time = time;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }

    public String getMealtypename() {
        return mealtypename;
    }

    public void setMealtypename(String mealtypename) {
        this.mealtypename = mealtypename;
    }

    public String getServing_qty() {
        return serving_qty;
    }

    public void setServing_qty(String serving_qty) {
        this.serving_qty = serving_qty;
    }

    public String getServing_unit() {
        return serving_unit;
    }

    public void setServing_unit(String serving_unit) {
        this.serving_unit = serving_unit;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

