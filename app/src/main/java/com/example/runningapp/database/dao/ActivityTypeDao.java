package com.example.runningapp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.runningapp.database.entity.ActivityType;

import java.util.List;


// Validates SQL at compile-time and associates it with a method
// Queries must be executed on an separate thread
@Dao
public interface ActivityTypeDao {
    @Insert
    long insert(ActivityType activityType);

    @Update
    void update(ActivityType activityType);

    @Delete
    void delete(ActivityType activityType);

    @Query("DELETE FROM activity_type_table")
    void deleteActivityType();

    @Query("SELECT * FROM activity_type_table")
    LiveData<List<ActivityType>> getAllActivityTypes();

    @Query("SELECT * FROM activity_type_table WHERE type_id = :typeId")
    LiveData<List<ActivityType>> getActivityTypes(int typeId);

    @Query("SELECT * FROM activity_type_table WHERE type_id = :typeId")
    ActivityType getActivityType(int typeId);
}
