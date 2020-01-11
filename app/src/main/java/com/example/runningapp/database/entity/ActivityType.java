package com.example.runningapp.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Room entity for both creating tables and instantiating objects from the database
// Room uses annotations to to identify how each part of this class relates to an entity in the db, room uses this info to generate code

@Entity(tableName = "activity_type_table")
public class ActivityType implements Base {
    @PrimaryKey(autoGenerate = true)
    private int type_id;
    private String name;

    public ActivityType(String name) {
        this.name = name;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getName() {
        return name;
    }

    public int getType_id() {
        return type_id;
    }
}