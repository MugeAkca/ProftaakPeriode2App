package com.example.runningapp.activities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Locations {

    @PrimaryKey(autoGenerate = true)
    public int location_id;

    @ForeignKey(entity = Activities.class, parentColumns = "id", childColumns = "activity_id")
    public int Activity_id;

    @ColumnInfo(name = "longitude")
    public String longitude;

    @ColumnInfo(name = "time")
    public Date time;
}
