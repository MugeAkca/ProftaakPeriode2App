package com.example.runningapp.activities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Goals {
    @PrimaryKey
    public int goal_id;

    @ColumnInfo(name="Activity_type_id")
    public int Activity_type_id;

    @ColumnInfo(name="time_goal")
    public Date time_goal;

    @ColumnInfo(name="speed_goal")
    public int speed_goal;
}
