package com.example.runningapp.room.dao;

import android.location.Location;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.runningapp.activities.Activity_Types;
import com.example.runningapp.activities.Locations;

import java.util.List;

@Dao
public interface LocationsDao {

    @Query("SELECT * FROM Locations WHERE location_id IN (:location_id)")
    List<Activity_Types> loadAllByIds(int[] location_id);

    @Insert
    void insertAll(Locations... locations);

    @Delete
    void delete(Location location);
}
