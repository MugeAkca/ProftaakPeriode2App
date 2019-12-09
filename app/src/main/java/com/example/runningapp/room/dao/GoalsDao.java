package com.example.runningapp.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.runningapp.activities.Activity_Types;
import com.example.runningapp.activities.Goals;

import java.util.List;

@Dao
public interface GoalsDao {
    @Query("SELECT * FROM Goals")
    List<Goals> getAll();

    @Query("SELECT * FROM Goals WHERE goal_id IN (:goal_id)")
    List<Activity_Types> loadAllByIds(int[] goal_id);

    @Insert
    void insertAll(Goals... goals);

    @Delete
    void delete(Goals goals);

}
