package com.example.runningapp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Update;

import com.example.runningapp.database.entity.Goal;
import com.example.runningapp.database.entity.GoalActivitySubType;

import java.util.List;


// Validates SQL at compile-time and associates it with a method
// Queries must be executed on an separate thread
@Dao
public interface GoalDao {

    @Insert
    long insert(Goal goal);

    @Update
    void update(Goal goal);

    @Delete
    void delete(Goal goal);

    //@Query is used to create custom query
    @Query("DELETE FROM goal_table")
    void deleteAllGoals();

    //LiveData observes table live. whenever data changes to db. It will updateActivity data to view.
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * FROM goal_table LEFT JOIN activity_type_table ON goal_table.activity_type_id=activity_type_table.type_id")
    LiveData<List<GoalActivitySubType>> getAllGoals2();

}
