package com.innovellent.curight.model;

/**
 * Created by Mak on 1/3/2018.
 */

public class Category_List {
    private Long foodcategoryid;
    private String categoryname;

    public Category_List(Long foodcategoryid, String categoryname) {
        this.foodcategoryid = foodcategoryid;
        this.categoryname = categoryname;
    }

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
