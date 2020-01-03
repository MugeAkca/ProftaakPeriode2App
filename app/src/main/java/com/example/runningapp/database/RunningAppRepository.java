package com.example.runningapp.database;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.runningapp.database.dao.ActivityDao;
import com.example.runningapp.database.dao.ActivityTypeDao;
import com.example.runningapp.database.dao.GoalDao;
import com.example.runningapp.database.entity.Activity;
import com.example.runningapp.database.entity.ActivityType;
import com.example.runningapp.database.entity.Goal;

import java.util.List;

// A Repository class abstracts access to multiple data sources
// Is a suggested best practice for code separation and architecture
// A Repository class provides a clean API for data access to the rest of the application.
// A Repository manages queries and allows you to use multiple backends. In the most common example, the Repository implements the logic for deciding whether to fetch data from a network or use results cached in a local database.
public class RunningAppRepository {

    private ActivityDao activityDao;
    private ActivityTypeDao activityTypeDao;
    private GoalDao goalDao;
    private LiveData<List<Activity>> allActivities;
    private LiveData<List<ActivityType>> allActivityTypes;
    private LiveData<List<Goal>> allGoals;


    // Activity
    // application sub class context, you can use it as context for creating db instance
    public RunningAppRepository(Application application) {
        RunningAppDatabase database = RunningAppDatabase.getInstance(application);

        // method abstract database class, normally you can't call this abstract class but because room generates this class with the builder it generates all the necesary code for this method
        activityDao = database.activityDao();
        allActivities = activityDao.getAllActivities();
        activityTypeDao = database.activityTypeDao();
        allActivityTypes = activityTypeDao.getAllActivityTypes();
        goalDao = database.goalDao();
        allGoals = goalDao.getAllGoals();

    }

    public void insertActivity(Activity activity) {
        new InsertActivityAsyncTask(activityDao).execute(activity);
    }

    public void updateActivity(Activity activity) {
        new UpdateActivityAsyncTask(activityDao).execute(activity);
    }

    public void deleteActivity(Activity activity) {
        new DeleteActivityAsyncTask(activityDao).execute(activity);
    }

    public void deleteAllActivities() {
        new DeleteAllActivitiesAsyncTask(activityDao).execute();
    }

    public void insertActivityType(ActivityType activityType) {
        new InsertActivityTypeAsyncTask(activityTypeDao).execute(activityType);
    }

    public void updateActivityType(ActivityType activityType) {
        new UpdateActivityTypeAsyncTask(activityTypeDao).execute(activityType);
    }

    public void deleteActivityType(ActivityType activityType) {
        new DeleteActivityTypeAsyncTask(activityTypeDao).execute(activityType);
    }

    public void deleteAllActivityTypes() {
        new DeleteAllActivityTypesAsyncTask(activityTypeDao).execute();
    }


    public void insertGoal(Goal goal) {
        new InsertGoalAsyncTask(goalDao).execute(goal);
    }

    public void updateGoal(Goal goal) {
        new UpdateGoalAsyncTask(goalDao).execute(goal);
    }

    public void deleteGoal(Goal goal) {
        new DeleteGoalAsyncTask(goalDao).execute(goal);
    }

    public void deleteAllGoals() {
        new DeleteAllGoalsAsyncTask(goalDao).execute();
    }

    public LiveData<List<Goal>> getAllGoals() {
        return allGoals;
    }

    // auto background thread
    public LiveData<List<Activity>> getAllActivities() {
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
    private static class DeleteAllActivitiesAsyncTask extends AsyncTask<Void, Void, Void> {

        private ActivityDao activityDao;

        private DeleteAllActivitiesAsyncTask(ActivityDao activityDao) {
            this.activityDao = activityDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            activityDao.deleteAll();
            return null;
        }
    }

    public LiveData<List<ActivityType>> getAllActivityTypes() {
        return allActivityTypes;
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

    private static class InsertGoalAsyncTask extends AsyncTask<Goal, Void, Void> {

        private GoalDao goalDao;

        private InsertGoalAsyncTask(GoalDao goalDao) {
            this.goalDao = goalDao;
        }

        @Override
        protected Void doInBackground(Goal... goals) {
            goalDao.insert(goals[0]);
            return null;
        }
    }

    //
    private static class UpdateGoalAsyncTask extends AsyncTask<Goal, Void, Void> {

        private GoalDao goalDao;

        private UpdateGoalAsyncTask(GoalDao goalDao) {
            this.goalDao = goalDao;
        }

        @Override
        protected Void doInBackground(Goal... goals) {
            goalDao.update(goals[0]);
            return null;
        }
    }

    //
    private static class DeleteGoalAsyncTask extends AsyncTask<Goal, Void, Void> {
        private GoalDao goalDao;

        private DeleteGoalAsyncTask(GoalDao goalDao) {
            this.goalDao = goalDao;
        }

        @Override
        protected Void doInBackground(Goal... goals) {
            goalDao.delete(goals[0]);
            return null;
        }
    }

    //
    private static class DeleteAllGoalsAsyncTask extends AsyncTask<Void, Void, Void> {

        private GoalDao goalDao;

        private DeleteAllGoalsAsyncTask(GoalDao goalDao) {
            this.goalDao = goalDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            goalDao.deleteAllGoals();
            return null;
        }
    }
}
