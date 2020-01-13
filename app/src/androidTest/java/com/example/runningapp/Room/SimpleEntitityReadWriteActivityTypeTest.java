package com.example.runningapp.Room;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.runningapp.database.RunningAppDatabase;
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
    private ActivityTypeDao ActivityTypeDao;
    private RunningAppDatabase db;
    private long insertActivityTypeId;
    private long getActivityTypeId;
    private ActivityType ActivityType;
    private ActivityType getActivityType;


    @Before
    public void createDb(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, RunningAppDatabase.class).build();
        ActivityTypeDao = db.activityTypeDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void writeActivityTypeAndReadInList() throws Exception{
        ActivityType = new ActivityType("activityTypeName");
        insertActivityTypeId = ActivityTypeDao.insert(ActivityType);
        getActivityType = ActivityTypeDao.getActivityType((int) insertActivityTypeId);
        getActivityTypeId = getActivityType.getType_id();
        assertEquals(insertActivityTypeId, getActivityTypeId);
    }
}
