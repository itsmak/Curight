package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mak on 1/5/2018.
 */

public class Food_Units {

    @SerializedName("foodid")
    private Long foodid;
    @SerializedName("unit")
    private String unit;
    @SerializedName("itemname")
    private String itemname;
    @SerializedName("protein")
    private String protein;
    @SerializedName("fat")
    private String fat;
    @SerializedName("fiber")
    private Long fiber;
    @SerializedName("calories")
    private Long calories;

    public Long getFoodid()
    {
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

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
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
