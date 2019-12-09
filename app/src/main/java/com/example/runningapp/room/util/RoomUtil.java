package com.example.runningapp.room.util;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.runningapp.room.dao.ActivitiesDao;
import com.example.runningapp.room.dao.Activity_TypesDao;
import com.example.runningapp.room.dao.GoalsDao;
import com.example.runningapp.room.dao.LocationsDao;
import com.example.runningapp.activities.Activities;
import com.example.runningapp.activities.Activity_Types;

public class RoomUtil {
    @Database(entities = {Activities.class, Activity_Types.class, GoalsDao.class, LocationsDao.class}, version = 1)
    public abstract class AppDatabase extends RoomDatabase {
        public abstract ActivitiesDao activitiesDao();
        public abstract Activity_TypesDao activity_typesDao();
        public abstract GoalsDao goalsDao();
        public abstract LocationsDao locationsDao();
    }

}
