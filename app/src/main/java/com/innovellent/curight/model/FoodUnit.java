package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

public class FoodUnit {
    @SerializedName("foodid")
    private Long foodid;
    private String unit;
    private Long carbs;
    private Long protein;
    private Long fat;
    private Long fiber;
    private Long calories;

    public FoodUnit(Long foodid, String unit, Long carbs, Long protein, Long fat, Long fiber, Long calories) {
        this.foodid = foodid;
        this.unit = unit;
        this.carbs = carbs;
        this.protein = protein;
        this.fat = fat;
        this.fiber = fiber;
        this.calories = calories;
    }

    public Long getFoodid() {
        return foodid;
    }

    public void setFoodid(Long foodid) {
        this.foodid = foodid;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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
