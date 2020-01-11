//package com.example.runningapp;
//
//import android.content.Context;
//
//import androidx.room.Room;
//
//import com.example.runningapp.database.dao.ActivityDao;
//import com.example.runningapp.database.dao.ActivityTypeDao;
//import com.example.runningapp.database.dao.GoalDao;
//import com.example.runningapp.database.dao.LocationDao;
//import com.example.runningapp.database.entity.Activity;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import java.io.IOException;
//
//@RunWith(AndroidJUnit4.class)
//public class SimpleEntitReadWriteTest {
//    private ActivityDao activityDao;
//    private ActivityTypeDao activityTypeDao;
//    private GoalDao goalDao;
//    private LocationDao locationDao;
//    private TestDatabase db;
//
//    @Before
//    public void createDb() {
//        Context context = ApplicationProvider.getApplicationContext();
//        db = Room.inMemoryDatabaseBuilder(context, TestDatabase.class).build();
//        activityDao = db.getActivityDao();
//        goalDao = db.getGoalDao();
//        activityTypeDao = db.getActivityTypeDao();
//        locationDao = db.getLocationDao();
//    }
//
//    @After
//    public void closeDb() throws IOException{
//        db.close();
//    }
//
//    @Test
//    public void writeActivityInList() throws Exception{
//        Activity activity = new Activity("1", "2", "2");
//
//    }
//}