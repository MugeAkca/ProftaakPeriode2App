package com.example.runningapp.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;
import java.util.Date;

// Room entity for both creating tables and instantiating objects from the database
// Room uses annotations to to identify how each part of this class relates to an entity in the db, room uses this info to generate code
@Entity(tableName = "location_table")
public class Location implements Base {
    @PrimaryKey(autoGenerate = true)
    private long location_id;
    private int activity_id;
    private String longitude;
    private String latitude;
    private Time time;

    public Location(int activity_id, String longitude, String latitude, String time) {
        this.activity_id = activity_id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.time = Time.valueOf(time);
    }

    public Location(long location_id, int activity_id, String longitude, String latitude, Time time) {
        this.location_id = location_id;
        this.activity_id = activity_id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.time = time;
    }

    public long getLocation_id() {
        return location_id;
    }

    public void setLocation_id(long location_id) {
        this.location_id = location_id;
    }

    public int getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(int activity_id) {
        this.activity_id = activity_id;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
