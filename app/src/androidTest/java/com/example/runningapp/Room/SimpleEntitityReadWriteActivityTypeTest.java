package com.example.runningapp.Room;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.runningapp.database.RunningAppDatabase;
import com.example.runningapp.database.dao.ActivityDao;
import com.example.runningapp.database.dao.ActivityTypeDao;
import com.example.runningapp.database.entity.ActivityType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class SimpleEntitityReadWriteActivityTypeTest {
    private ActivityTypeDao activityTypeDao;
    private RunningAppDatabase db;
    private String getActivityTypeName;
    private long getActivityTypeId;
    private ActivityType activityType;
    private ActivityType getActivityType;
    private long insertActivityTypeId;
    private String activityTypeName = "activityTypeName";


    @Before
    public void createDb(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, RunningAppDatabase.class).build();
        activityTypeDao = db.activityTypeDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void writeActivityTypeAndReadInList(){
        activityType = new ActivityType(activityTypeName);
        insertActivityTypeId = activityTypeDao.insert(activityType);
        getActivityType = activityTypeDao.getActivityType((int) insertActivityTypeId);
        getActivityTypeName = getActivityType.getName();
        assertEquals(activityTypeName, getActivityTypeName);
    }
}
