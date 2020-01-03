package com.example.runningapp.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;
import java.util.Date;

// Room entity for both creating tables and instantiating objects from the database
// Room uses annotations to to identify how each part of this class relates to an entity in the db, room uses this info to generate code
@Entity(tableName = "location_table")
public class Location {
    @PrimaryKey(autoGenerate = true)
    private int location_id;
    private int activity_id;
    private String longitude;
    private String latitude;
    private Time time;

    public Location(int activity_id, String longitude, String latitude, Time time) {
        this.activity_id = activity_id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.time = time;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public int getLocation_id() {
        return location_id;
    }

    public int getActivity_id() {
        return activity_id;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public Time getTime() {
        return time;
    }
}
