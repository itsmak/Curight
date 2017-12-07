package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pramod on 16-Nov-2017.
 */

public class Lunch {
    @SerializedName("userid")
    private String userid;
    private String foodconsumptionid;
    private String foodid;
    private Long quantity;
    private String foodname;
    private String serving_qty;
    private String date;
    private String time;
    private String mealtypename;
    private String serving_unit;
    private Long modifiedby;
    private Long carbs;
    private Long fat;
    private Long fiber;
    private Long protein;
    private Long calories;

    public String getFoodconsumptionid() {
        return foodconsumptionid;
    }

    public void setFoodconsumptionid(String foodconsumptionid) {
        this.foodconsumptionid = foodconsumptionid;
    }

    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getServing_qty() {
        return serving_qty;
    }

    public void setServing_qty(String serving_qty) {
        this.serving_qty = serving_qty;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMealtypename() {
        return mealtypename;
    }

    public void setMealtypename(String mealtypename) {
        this.mealtypename = mealtypename;
    }

    public String getServing_unit() {
        return serving_unit;
    }

    public void setServing_unit(String serving_unit) {
        this.serving_unit = serving_unit;
    }

    public Long getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(Long modifiedby) {
        this.modifiedby = modifiedby;
    }

    public Long getCarbs() {
        return carbs;
    }

    public void setCarbs(Long carbs) {
        this.carbs = carbs;
    }

    public Long getFat() {
        return fat;
    }

    public void setFat(Long fat) {
        this.fat = fat;
    }

    public Long getFiber() {
        return fiber;
    }

    public void setFiber(Long fiber) {
        this.fiber = fiber;
    }

    public Long getProtein() {
        return protein;
    }

    public void setProtein(Long protein) {
        this.protein = protein;
    }

    public Long getCalories() {
        return calories;
    }

    public void setCalories(Long calories) {
        this.calories = calories;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
