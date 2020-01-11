package com.example.runningapp.database.entity;

import java.sql.Time;

public class GoalActivitySubType extends Goal {

    private String name;

    public GoalActivitySubType(int activity_type_id, Time time_goal, int speed_goal, String name) {
        super(activity_type_id, time_goal, speed_goal);
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
