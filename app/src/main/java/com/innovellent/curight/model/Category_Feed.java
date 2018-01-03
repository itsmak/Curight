package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mak on 1/3/2018.
 */

public class Category_Feed {

    @SerializedName("foodcategoryid")
    private Long foodcategoryid;

    @SerializedName("categoryname")
    private String categoryname;

    public Long getFoodcategoryid() {
        return foodcategoryid;
    }

    public void setFoodcategoryid(Long foodcategoryid) {
        this.foodcategoryid = foodcategoryid;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }
}
