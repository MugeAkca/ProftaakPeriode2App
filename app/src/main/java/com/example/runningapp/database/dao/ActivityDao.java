package com.example.runningapp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Update;

import com.example.runningapp.database.entity.Activity;
import com.example.runningapp.database.entity.ActivityCategory;

import java.util.List;


// Validates SQL at compile-time and associates it with a method
// Queries must be executed on an separate thread
// retrieves all data from entity
@Dao
public interface ActivityDao {
    @Insert
    void insert(Activity activity);

    @Update
    void update(Activity activity);

    @Delete
    void delete(Activity activity);

    @Query("DELETE FROM activity_table")
    void deleteAll();

    @Query("SELECT * FROM activity_table")
    LiveData<List<Activity>> getAllActivities();

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM activity_table LEFT JOIN category ON activity_id = activity_type_id")
    LiveData<List<ActivityCategory>> getActivityActivityType();
}
