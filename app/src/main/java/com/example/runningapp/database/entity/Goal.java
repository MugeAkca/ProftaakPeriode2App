package com.example.runningapp.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;

import static java.lang.Integer.parseInt;

// Room entity for both creating tables and instantiating objects from the database
// Room uses annotations to to identify how each part of this class relates to an entity in the db, room uses this info to generate code
@Entity(tableName = "goal_table")
public class Goal {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int activity_type_id;
    private Time time_goal;
    private int speed_goal;

    public Goal(String activity_type_id, String time_goal, String speed_goal) {
        this.activity_type_id = parseInt(activity_type_id);
        this.time_goal = Time.valueOf(time_goal);
        this.speed_goal = parseInt(speed_goal);
    }

    public Goal(int activity_type_id, Time time_goal, int speed_goal) {
        this.activity_type_id = activity_type_id;
        this.time_goal = time_goal;
        this.speed_goal = speed_goal;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getActivity_type_id() {
        return activity_type_id;
    }

    public Time getTime_goal() {
        return time_goal;
    }

    public int getSpeed_goal() {
        return speed_goal;
    }


}
