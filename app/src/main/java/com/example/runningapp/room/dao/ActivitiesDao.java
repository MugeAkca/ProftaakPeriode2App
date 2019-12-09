package com.example.runningapp.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.runningapp.activities.Activities;
import com.example.runningapp.activities.Activity_Types;

import java.util.List;

@Dao
public interface ActivitiesDao {

    @Query("SELECT * FROM activities")
    List<Activities> getAll();

    @Query("SELECT * FROM activities WHERE activity_id IN (:activity_id)")
    List<Activity_Types> loadAllByIds(int[] activity_id);

    @Insert
    void insertAll(Activities... activities);

    @Delete
    void delete(Activities activities);
}
