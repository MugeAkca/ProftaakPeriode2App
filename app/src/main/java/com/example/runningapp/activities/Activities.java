package com.example.runningapp.activities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity
public class Activities {
    @PrimaryKey
    public int activity_id;

    @ForeignKey(entity = Activity_Types.class, parentColumns = "type_id", childColumns = "type_id")
    public int type_id;

    @ColumnInfo(name="startDate")
    public Date startDate;

    @ColumnInfo(name = "endDate")
    public Date endDate;
}
