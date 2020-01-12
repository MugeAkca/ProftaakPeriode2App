package com.example.runningapp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Update;

import com.example.runningapp.database.entity.Activity;
import com.example.runningapp.database.entity.ActivityActivitySubType;

import java.util.List;


// Validates SQL at compile-time and associates it with a method
// Queries must be executed on an separate thread
// retrieves all data from entity
@Dao
public interface ActivityDao {
    @Insert
    long insert(Activity activity);

    @Update
    void update(Activity activity);

    @Delete
    void delete(Activity activity);

    @Query("DELETE FROM activity_table")
    void deleteAll();

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM activity_table LEFT JOIN activity_type_table ON activity_table.activity_type_id = activity_type_table.type_id")
    LiveData<List<ActivityActivitySubType>> getAllActivities();

    @Query("SELECT * FROM activity_table WHERE id = :activityId")
    Activity getActivity(int activityId);

}
