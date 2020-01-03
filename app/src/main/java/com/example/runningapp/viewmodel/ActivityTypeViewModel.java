package com.example.runningapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.runningapp.database.RunningAppRepository;
import com.example.runningapp.database.entity.ActivityType;

import java.util.List;

// The ViewModel provides data to the UI and survives configuration changes.
// A ViewModel acts as a communication center between the Repository and the UI.
// You can also use a ViewModel to share data between fragments.
// Separating your app's UI data from your Activity and Fragment classes lets you better follow the single responsibility principle:
// Your activities are responsible for drawing data to the screen, while your ViewModel can take care of holding and processing all the data needed for the UI.
public class ActivityTypeViewModel extends AndroidViewModel {
    private RunningAppRepository repository;
    private LiveData<List<ActivityType>> allActivityTypes;

    public ActivityTypeViewModel(@NonNull Application application) {
        super(application);
        repository = new RunningAppRepository(application);
        allActivityTypes = repository.getAllActivityTypes();
    }

    public void insert(ActivityType activityType) {
        repository.insertActivityType(activityType);
    }

    public void update(ActivityType activityType) {
        repository.updateActivityType(activityType);
    }

    public void delete(ActivityType activityType) {
        repository.deleteActivityType(activityType);
    }

    public void deleteAllActivityTypes() {
        repository.deleteAllActivityTypes();
    }

    public LiveData<List<ActivityType>> getAllActivityTypes() {
        return allActivityTypes;
    }
}
