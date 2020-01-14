package com.example.runningapp.Room;


import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.runningapp.database.RunningAppDatabase;
import com.example.runningapp.database.dao.ActivityDao;
import com.example.runningapp.database.dao.GoalDao;
import com.example.runningapp.database.entity.Goal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SimpleEntitityReadWriteGoalsTest {
    private GoalDao goalDao;
    private RunningAppDatabase db;
    private long insertGoalId;
    private long getGoalId;
    private Goal goal;
    private long getGoal;


    @Before
    public void createDb(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, RunningAppDatabase.class).build();
        goalDao = db.goalDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void writeGoalAndReadInList() {

    }
}
