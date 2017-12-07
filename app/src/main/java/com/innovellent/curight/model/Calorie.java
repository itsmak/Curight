package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pramod on 16-Nov-2017.
 */

public class Calorie {
    @SerializedName("foodid")
    private String foodid;
    private String quantity;

    public Calorie(String foodid, String quantity) {
        this.foodid = foodid;
        this.quantity = quantity;
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
}
