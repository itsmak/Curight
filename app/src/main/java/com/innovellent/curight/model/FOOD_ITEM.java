package com.innovellent.curight.model;

/**
 * Created by Mak on 3/22/2018.
 */

public class FOOD_ITEM {

    private String foodname;
    private String foodquantity;
    private String foodunit;
    private String foodcaloryunit;

    public FOOD_ITEM(String foodname, String foodquantity, String foodunit, String foodcaloryunit) {
        this.foodname = foodname;
        this.foodquantity = foodquantity;
        this.foodunit = foodunit;
        this.foodcaloryunit = foodcaloryunit;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getFoodquantity() {
        return foodquantity;
    }

    public void setFoodquantity(String foodquantity) {
        this.foodquantity = foodquantity;
    }

    public String getFoodunit() {
        return foodunit;
    }

    public void setFoodunit(String foodunit) {
        this.foodunit = foodunit;
    }

    public String getFoodcaloryunit() {
        return foodcaloryunit;
    }

    public void setFoodcaloryunit(String foodcaloryunit) {
        this.foodcaloryunit = foodcaloryunit;
    }
}
