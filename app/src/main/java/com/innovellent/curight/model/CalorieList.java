package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mak on 3/6/2018.
 */

public class CalorieList {

    @SerializedName("foodid")
    private int foodid;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("carbs")
    private int carbs;
    @SerializedName("protein")
    private int protein;
    @SerializedName("fat")
    private int fat;
    @SerializedName("fiber")
    private int fiber;
    @SerializedName("calories")
    private int calories;

    public CalorieList(int foodid, int quantity, int carbs, int protein, int fat, int fiber, int calories) {
        this.foodid = foodid;
        this.quantity = quantity;
        this.carbs = carbs;
        this.protein = protein;
        this.fat = fat;
        this.fiber = fiber;
        this.calories = calories;
    }

    public int getFoodid() {
        return foodid;
    }

    public void setFoodid(int foodid) {
        this.foodid = foodid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getFiber() {
        return fiber;
    }

    public void setFiber(int fiber) {
        this.fiber = fiber;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}
