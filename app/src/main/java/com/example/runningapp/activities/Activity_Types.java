package com.example.runningapp.activities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Activity_Types {

    @PrimaryKey
    public int type_id;

    @ColumnInfo(name = "name")
    public String name;
}
