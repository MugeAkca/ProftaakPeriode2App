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
    void insert(com.example.runningapp.database.entity.Location location);

    @Update
    void update(com.example.runningapp.database.entity.Location location);

    @Delete
    void delete(com.example.runningapp.database.entity.Location location);

    @Query("DELETE FROM location_table")
    void deleteAll(com.example.runningapp.database.entity.Location location);

    @Query("SELECT * FROM location_table")
    LiveData<List<com.example.runningapp.database.entity.Location>> getAllLocation();
}
