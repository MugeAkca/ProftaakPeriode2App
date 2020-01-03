package com.example.runningapp.database;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.runningapp.database.dao.ActivityDao;
import com.example.runningapp.database.entity.Activity;

import java.util.List;

// A Repository class abstracts access to multiple data sources
// Is a suggested best practice for code separation and architecture
// A Repository class provides a clean API for data access to the rest of the application.

// A Repository manages queries and allows you to use multiple backends. In the most common example, the Repository implements the logic for deciding whether to fetch data from a network or use results cached in a local database.
public class ActivityRepository {

    private ActivityDao activityDao;
    private LiveData<List<Activity>> allActivities;

    public ActivityRepository(Application application) {
        RunningAppDatabase database = RunningAppDatabase.getInstance(application);
        activityDao = database.activityDao();
        allActivities = activityDao.getAllActivities();
    }

    public void insert(Activity activity) {
        new InsertActivityAsyncTask(activityDao).execute(activity);
    }

    public void update(Activity activity) {
        new UpdateActivityAsyncTask(activityDao).execute(activity);
    }

    public void delete(Activity activity) {
        new DeleteActivityAsyncTask(activityDao).execute(activity);
    }

    public void deleteAllActivitys() {
        new DeleteAllActivitysAsyncTask(activityDao).execute();
    }

    public LiveData<List<Activity>> getAllActivitys() {
        return allActivities;
    }

    private static class InsertActivityAsyncTask extends AsyncTask<Activity, Void, Void> {

        private ActivityDao activityDao;

        private InsertActivityAsyncTask(ActivityDao activityDao) {
            this.activityDao = activityDao;
        }

        @Override
        protected Void doInBackground(Activity... activitys) {
            activityDao.insert(activitys[0]);
            return null;
        }
    }

    //
    private static class UpdateActivityAsyncTask extends AsyncTask<Activity, Void, Void> {

        private ActivityDao activityDao;

        private UpdateActivityAsyncTask(ActivityDao activityDao) {
            this.activityDao = activityDao;
        }

        @Override
        protected Void doInBackground(Activity... activitys) {
            activityDao.update(activitys[0]);
            return null;
        }
    }

    //

    private static class DeleteActivityAsyncTask extends AsyncTask<Activity, Void, Void> {
        private ActivityDao activityDao;

        private DeleteActivityAsyncTask(ActivityDao activityDao) {
            this.activityDao = activityDao;
        }

        @Override
        protected Void doInBackground(Activity... activitys) {
            activityDao.delete(activitys[0]);
            return null;
        }
    }

    //
    private static class DeleteAllActivitysAsyncTask extends AsyncTask<Void, Void, Void> {

        private ActivityDao activityDao;

        private DeleteAllActivitysAsyncTask(ActivityDao activityDao) {
            this.activityDao = activityDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            activityDao.deleteAll();
            return null;
        }
    }
}
