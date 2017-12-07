package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pramod on 16-Nov-2017.
 */

public class CalorieRes {
    @SerializedName("foodid")
    private String foodid;
    private String quantity;
    private Long carbs;
    private Long protein;
    private Long fat;
    private Long fiber;
    private Long calories;

    public CalorieRes(String foodid, String quantity, Long carbs, Long protein, Long fat, Long fiber, Long calories) {
        this.foodid = foodid;
        this.quantity = quantity;
        this.carbs = carbs;
        this.protein = protein;
        this.fat = fat;
        this.fiber = fiber;
        this.calories = calories;
    }

    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Long getCarbs() {
        return carbs;
    }

    public void setCarbs(Long carbs) {
        this.carbs = carbs;
    }

    public Long getProtein() {
        return protein;
    }

    public void setProtein(Long protein) {
        this.protein = protein;
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

    public Long getCalories() {
        return calories;
    }

    public void setCalories(Long calories) {
        this.calories = calories;
    }
}
