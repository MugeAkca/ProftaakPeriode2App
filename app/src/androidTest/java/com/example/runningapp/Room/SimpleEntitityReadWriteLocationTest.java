package com.example.runningapp.Room;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.runningapp.database.RunningAppDatabase;
import com.example.runningapp.database.dao.ActivityDao;
import com.example.runningapp.database.entity.Activity;
import com.example.runningapp.database.entity.Location;
import com.example.runningapp.viewmodel.ActivityViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class SimpleEntitityReadWriteLocationTest {
    private com.example.runningapp.database.dao.LocationDao LocationDao;
    private RunningAppDatabase db;
    private long insertLocationId;
    private long getLocationId;
    private com.example.runningapp.database.entity.Location Location;
    private long getLocation;
    private Date date;
    private Activity activity;
    private long insertActivityId;
    private Activity getActivity;
    private int getActivityId;
    private ActivityDao activityDao;


    @Before
    public void createDb(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, RunningAppDatabase.class).build();
        LocationDao = db.locationDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void writeLocationAndReadInList() throws Exception{
        Location = new Location(1,String.valueOf(37.422), String.valueOf(-122.084), "1368590417000");
        insertLocationId = LocationDao.insert(Location);
        getLocation = LocationDao.getLocation(insertLocationId).getLocation_id();
        assertEquals(insertLocationId, getLocation);
    }
}
