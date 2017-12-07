package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Pramod on 16-Nov-2017.
 */

public class Food {
    private ArrayList<Lunch> BreakFast;
    private ArrayList<Lunch> Lunch;
    private ArrayList<Lunch> Snacks;
    private ArrayList<Lunch> Dinner;

    public Food(ArrayList<com.innovellent.curight.model.Lunch> breakFast, ArrayList<com.innovellent.curight.model.Lunch> lunch, ArrayList<com.innovellent.curight.model.Lunch> snacks, ArrayList<com.innovellent.curight.model.Lunch> dinner) {
        BreakFast = breakFast;
        Lunch = lunch;
        Snacks = snacks;
        Dinner = dinner;
    }

    public ArrayList<com.innovellent.curight.model.Lunch> getBreakFast() {
        return BreakFast;
    }

    public void setBreakFast(ArrayList<com.innovellent.curight.model.Lunch> breakFast) {
        BreakFast = breakFast;
    }

    public ArrayList<com.innovellent.curight.model.Lunch> getLunch() {
        return Lunch;
    }

    public void setLunch(ArrayList<com.innovellent.curight.model.Lunch> lunch) {
        Lunch = lunch;
    }

    public ArrayList<com.innovellent.curight.model.Lunch> getSnacks() {
        return Snacks;
    }

    public void setSnacks(ArrayList<com.innovellent.curight.model.Lunch> snacks) {
        Snacks = snacks;
    }

    public ArrayList<com.innovellent.curight.model.Lunch> getDinner() {
        return Dinner;
    }

    public void setDinner(ArrayList<com.innovellent.curight.model.Lunch> dinner) {
        Dinner = dinner;
    }
}
