package com.example.runningapp.database.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static java.lang.Integer.parseInt;

// Room entity for both creating sqlite tables and instantiating objects from the database
// Room uses annotations to to identify how each part of this class relates to an entity in the db, room uses this info to generate code
@Entity(tableName = "activity_table")
public class Activity implements Base {

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int activity_type_id;
    private String start_date;
    private String end_date;

    public Activity(String activity_type_id, String start_date, String end_date) {
        this.activity_type_id = parseInt(activity_type_id);
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Activity(int activity_type_id, String start_date, String end_date) {
        this.activity_type_id = activity_type_id;
        this.start_date = start_date;
        this.end_date = end_date;
    }



    public int getId() {
        return id;
    }

    public int getActivity_type_id() {
        return activity_type_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }
}
