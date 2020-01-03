package com.example.runningapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.runningapp.database.RunningAppRepository;
import com.example.runningapp.database.entity.Activity;

import java.util.List;

// The ViewModel provides data to the UI and survives configuration changes.
// A ViewModel acts as a communication center between the Repository and the UI.
// You can also use a ViewModel to share data between fragments.
// Separating your app's UI data from your Activity and Fragment classes lets you better follow the single responsibility principle:
// Your activities are responsible for drawing data to the screen, while your ViewModel can take care of holding and processing all the data needed for the UI.
public class ActivityViewModel extends AndroidViewModel {
    private RunningAppRepository repository;
    private LiveData<List<Activity>> allActivitys;

    public ActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new RunningAppRepository(application);
        allActivitys = repository.getAllActivities();
    }

    public void insert(Activity activity) {
        repository.insertActivity(activity);
    }

    public void update(Activity activity) {
        repository.updateActivity(activity);
    }

    public void delete(Activity activity) {
        repository.deleteActivity(activity);
    }

    public void deleteAllActivitys() {
        repository.deleteAllActivities();
    }

    public LiveData<List<Activity>> getAllActivitys() {
        return allActivitys;
    }
}
