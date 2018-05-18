package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mak on 5/17/2018.
 */

public class CategoryResult {

    @SerializedName("foodcategoryid")
    private Integer foodcategoryid;

    @SerializedName("categoryname")
    private String categoryname;

    public Integer getFoodcategoryid() {
        return foodcategoryid;
    }

    public void setFoodcategoryid(Integer foodcategoryid) {
        this.foodcategoryid = foodcategoryid;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }
}
