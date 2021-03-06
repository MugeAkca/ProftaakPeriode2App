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
// Separating the app's UI data from the Activity and Fragment classes lets you better follow the single responsibility principle:
// the activities are responsible for drawing data to the screen, while the ViewModel can take care of holding and processing all the data needed for the UI.
// store and proces data ui and communicate with model
// data doesnt get lost config changes viewmodel only removed memory as lifecycle activity is over (activity finhised or detached)
// never store or reference activity because the viewmodel is designed to outlive activity after it is destroyed (memory leaks)
// We exctend viewmodel we can pass data down to the database
public class ActivityTypeViewModel extends AndroidViewModel {
    private RunningAppRepository repository;
    private LiveData<List<ActivityType>> allActivityTypes;
    private List<ActivityType> allActivityType;

    public ActivityTypeViewModel(@NonNull Application application) {
        super(application);
        repository = new RunningAppRepository(application);
        allActivityTypes = repository.getAllActivityTypes();
//        allActivityType = repository.getAllActivityTypesSpinner();
    }

    public void insert(ActivityType activityType) {
        repository.insert(activityType);
    }

    public void update(ActivityType activityType) {
        repository.update(activityType);
    }

    public void delete(ActivityType activityType) {
        repository.delete(activityType);
    }

    public LiveData<List<ActivityType>> getAllActivityTypes() {
        return allActivityTypes;
    }

//    public List<ActivityType> getAllActivityTypesSpinner(){
//        return allActivityType;
//    }

}