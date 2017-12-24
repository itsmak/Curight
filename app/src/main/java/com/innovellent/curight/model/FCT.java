package com.innovellent.curight.model;

/**
 * Created by SUNIL on 12/21/2017.
 */

public class FCT {

    private String normalperiodduration,gap,reminderdays,currentperioddate,miss,notes;

    public int getFemalecycletrackid() {
        return femalecycletrackid;
    }

    public void setFemalecycletrackid(int femalecycletrackid) {
        this.femalecycletrackid = femalecycletrackid;
    }

    private int femalecycletrackid;



    public String getNormalperiodduration() {
        return normalperiodduration;
    }

    public void setNormalperiodduration(String normalperiodduration) {
        this.normalperiodduration = normalperiodduration;
    }

    public String getGap() {
        return gap;
    }

    public void setGap(String gap) {
        this.gap = gap;
    }

    public String getReminderdays() {
        return reminderdays;
    }

    public void setReminderdays(String reminderdays) {
        this.reminderdays = reminderdays;
    }

    public String getCurrentperioddate() {
        return currentperioddate;
    }

    public void setCurrentperioddate(String currentperioddate) {
        this.currentperioddate = currentperioddate;
    }

    public String getMiss() {
        return miss;
    }

    public void setMiss(String miss) {
        this.miss = miss;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
