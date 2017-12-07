package com.innovellent.curight.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Pramod on 16-Nov-2017.
 */

public class SetGoal {
    @SerializedName("goalid")
    private Integer goalid;
    private Long goal;

    public SetGoal(Integer goalid, Long goal) {
        this.goalid = goalid;
        this.goal = goal;
    }

    public Integer getGoalid() {
        return goalid;
    }

    public void setGoalid(Integer goalid) {
        this.goalid = goalid;
    }

    public Long getGoal() {
        return goal;
    }

    public void setGoal(Long goal) {
        this.goal = goal;
    }
}
