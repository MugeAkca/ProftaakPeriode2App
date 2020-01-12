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
    private long activity_id;
    private Double longitude;
    private Double latitude;
    private Long time;

    public Location(long activity_id, Double longitude, Double latitude, Long time) {
        this.activity_id = activity_id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.time = time;
    }

    public Location(int activity_id, String longitude, String latitude, String time) {
        this.activity_id = activity_id;
        this.longitude = Double.valueOf(longitude);
        this.latitude = Double.valueOf(latitude);
        this.time = Long.valueOf(time);
    }


    public long getLocation_id() {
        return location_id;
    }

    public void setLocation_id(long location_id) {
        this.location_id = location_id;
    }

    public long getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(long activity_id) {
        this.activity_id = activity_id;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
