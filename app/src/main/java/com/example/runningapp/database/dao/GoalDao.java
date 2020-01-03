package com.example.runningapp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.runningapp.database.entity.Goal;

import java.util.List;


// Validates SQL at compile-time and associates it with a method
// Queries must be executed on an separate thread
@Dao
public interface GoalDao {

    @Insert
    void insert(Goal goal);

    @Update
    void update(Goal goal);

    @Delete
    void delete(Goal goal);

    //@Query is used to create custom query
    @Query("DELETE FROM goal_table")
    void deleteAllGoals();

    //LiveData observes table live. whenever data changes to db. It will update data to view.
    @Query("SELECT * FROM goal_table")
    LiveData<List<Goal>> getAllGoals();

}
