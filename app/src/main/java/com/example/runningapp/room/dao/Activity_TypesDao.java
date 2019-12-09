package com.example.runningapp.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.runningapp.activities.Activity_Types;

import java.util.List;

@Dao
public interface Activity_TypesDao {

    @Query("SELECT * FROM Activity_Types WHERE type_id IN (:type_id)")
    List<Activity_Types> loadAllByIds(int[] type_id);

    @Insert
    void insertAll(Activity_Types... Activity_Types);

    @Delete
    void delete(Activity_Types activity_types);
}
