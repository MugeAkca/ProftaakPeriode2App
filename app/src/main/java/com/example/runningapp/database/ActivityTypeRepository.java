package com.example.runningapp.database;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.runningapp.database.dao.ActivityTypeDao;
import com.example.runningapp.database.entity.ActivityType;

import java.util.List;

// A Repository class abstracts access to multiple data sources
// Is a suggested best practice for code separation and architecture
// A Repository class provides a clean API for data access to the rest of the application.

// A Repository manages queries and allows you to use multiple backends. In the most common example, the Repository implements the logic for deciding whether to fetch data from a network or use results cached in a local database.
public class ActivityTypeRepository {

    private ActivityTypeDao activityTypeDao;
    private LiveData<List<ActivityType>> allActivities;

    public ActivityTypeRepository(Application application) {
        RunningAppDatabase database = RunningAppDatabase.getInstance(application);
        activityTypeDao = database.activityTypeDao();
        allActivities = activityTypeDao.getAllActivityTypes();
    }

    public void insert(ActivityType activityType) {
        new InsertActivityTypeAsyncTask(activityTypeDao).execute(activityType);
    }

    public void update(ActivityType activityType) {
        new UpdateActivityTypeAsyncTask(activityTypeDao).execute(activityType);
    }

    public void delete(ActivityType activityType) {
        new DeleteActivityTypeAsyncTask(activityTypeDao).execute(activityType);
    }

    public void deleteAllActivityTypes() {
        new DeleteAllActivityTypesAsyncTask(activityTypeDao).execute();
    }

    public LiveData<List<ActivityType>> getAllActivityTypes() {
        return allActivities;
    }

    private static class InsertActivityTypeAsyncTask extends AsyncTask<ActivityType, Void, Void> {

        private ActivityTypeDao activityTypeDao;

        private InsertActivityTypeAsyncTask(ActivityTypeDao activityTypeDao) {
            this.activityTypeDao = activityTypeDao;
        }

        @Override
        protected Void doInBackground(ActivityType... activityTypes) {
            activityTypeDao.insert(activityTypes[0]);
            return null;
        }
    }

    //
    private static class UpdateActivityTypeAsyncTask extends AsyncTask<ActivityType, Void, Void> {

        private ActivityTypeDao activityTypeDao;

        private UpdateActivityTypeAsyncTask(ActivityTypeDao activityTypeDao) {
            this.activityTypeDao = activityTypeDao;
        }

        @Override
        protected Void doInBackground(ActivityType... activityTypes) {
            activityTypeDao.update(activityTypes[0]);
            return null;
        }
    }

    //

    private static class DeleteActivityTypeAsyncTask extends AsyncTask<ActivityType, Void, Void> {
        private ActivityTypeDao activityTypeDao;

        private DeleteActivityTypeAsyncTask(ActivityTypeDao activityTypeDao) {
            this.activityTypeDao = activityTypeDao;
        }

        @Override
        protected Void doInBackground(ActivityType... activityTypes) {
            activityTypeDao.delete(activityTypes[0]);
            return null;
        }
    }

    //
    private static class DeleteAllActivityTypesAsyncTask extends AsyncTask<Void, Void, Void> {

        private ActivityTypeDao activityTypeDao;

        private DeleteAllActivityTypesAsyncTask(ActivityTypeDao activityTypeDao) {
            this.activityTypeDao = activityTypeDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            activityTypeDao.deleteActivityType();
            return null;
        }
    }
}
