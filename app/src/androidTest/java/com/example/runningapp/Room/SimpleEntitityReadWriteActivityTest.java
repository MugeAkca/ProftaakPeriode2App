package com.example.runningapp.Room;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.runningapp.database.RunningAppDatabase;
import com.example.runningapp.database.dao.ActivityDao;
import com.example.runningapp.database.entity.Activity;
import com.example.runningapp.viewmodel.ActivityViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class SimpleEntitityReadWriteActivityTest {
    private ActivityDao activityDao;
    private RunningAppDatabase db;
    private long insertActivityId;
    private long getActivityId;
    private Activity activity;
    private Activity getActivity;


    @Before
    public void createDb(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, RunningAppDatabase.class).build();
        activityDao = db.activityDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void writeActivityAndReadInList() throws Exception{
        activity = new Activity("1", "2202-03-02", "2203-03-03");
        insertActivityId = activityDao.insert(activity);
        getActivity = activityDao.getActivity((int) insertActivityId);
        getActivityId = getActivity.getId();
        assertEquals(insertActivityId, getActivityId);
    }
}
