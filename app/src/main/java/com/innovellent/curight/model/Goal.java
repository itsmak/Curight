package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pramod on 16-Nov-2017.
 */

public class Goal {
    @SerializedName("goalid")
    private Long goalid;
    private String userid;
    private Long goal;
    private Long consumption;
    private Long burn;
    private Long modifiedby;

    public Goal(Long goalid, String userid, Long goal, Long consumption, Long burn, Long modifiedby) {
        this.goalid = goalid;
        this.userid = userid;
        this.goal = goal;
        this.consumption = consumption;
        this.burn = burn;
        this.modifiedby = modifiedby;
    }

    public Long getGoalid() {
        return goalid;
    }

    public void setGoalid(Long goalid) {
        this.goalid = goalid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Long getGoal() {
        return goal;
    }

    public void setGoal(Long goal) {
        this.goal = goal;
    }

    public Long getConsumption() {
        return consumption;
    }

    public void setConsumption(Long consumption) {
        this.consumption = consumption;
    }

    public Long getBurn() {
        return burn;
    }

    public void setBurn(Long burn) {
        this.burn = burn;
    }

    public Long getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(Long modifiedby) {
        this.modifiedby = modifiedby;
    }
}
