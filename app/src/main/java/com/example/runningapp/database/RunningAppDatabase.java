package com.example.runningapp.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.runningapp.database.dao.ActivityDao;
import com.example.runningapp.database.dao.ActivityTypeDao;
import com.example.runningapp.database.dao.GoalDao;
import com.example.runningapp.database.dao.LocationDao;
import com.example.runningapp.database.entity.Activity;
import com.example.runningapp.database.entity.ActivityType;
import com.example.runningapp.database.entity.Goal;
import com.example.runningapp.database.entity.Location;

// Implementing Room database using singleton design pattern to insure only one instance of the roomdatabase exists
@TypeConverters({Converter.class})
@Database(entities = {Activity.class, Goal.class, ActivityType.class, Location.class}, version = 42, exportSchema = true)
public abstract class RunningAppDatabase extends RoomDatabase {

    //instance variable is created, so that it can turn "RunningAppDatabase" class into a singleton.
    //singleton doesn't let start multiple instance of a class.
    private static RunningAppDatabase instance;

    //runningappDao() returns RunningappDao. And this method doesn't have a body.
    public abstract ActivityDao activityDao();

    public abstract GoalDao goalDao();

    public abstract ActivityTypeDao activityTypeDao();

    public abstract LocationDao locationDao();

    //synchronized means only one thread at a time can access this method.
    static synchronized RunningAppDatabase getInstance(Context context) {
        //initialize instance if there is none
        if (instance == null) {
            //new RunningAppDatabase can't be used because it is abstract. That's why we are using builder.
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    RunningAppDatabase.class, "runningapp_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomcallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomcallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };


    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private GoalDao goalDao;
        private ActivityDao activityDao;
        private ActivityTypeDao activityTypeDao;
        private LocationDao locationDao;

        private PopulateDbAsyncTask(RunningAppDatabase db) {
            activityDao = db.activityDao();
            goalDao = db.goalDao();
            activityTypeDao = db.activityTypeDao();
            locationDao = db.locationDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
