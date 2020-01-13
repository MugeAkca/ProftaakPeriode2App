package com.example.runningapp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.runningapp.database.entity.Location;

import java.util.List;


// Validates SQL at compile-time and associates it with a method
// Queries must be executed on an separate thread
@Dao
public interface LocationDao {
    @Insert
    long insert(Location location);

    @Update
    void update(Location location);

    @Delete
    void delete(Location location);

    /*
    @Query("DELETE FROM location_table")
    void deleteAll(Location location);
      */

    @Query("SELECT * FROM location_table WHERE activity_id = :activity_id")
    LiveData<List<Location>> getLocations(long activity_id);


    @Query("SELECT * FROM location_table WHERE activity_id = :activity_id LIMIT 1")
    Location getLocation(long activity_id);

    @Query("SELECT * FROM location_table")
    LiveData<List<Location>> getAllLocations();
}
