package com.example.runningapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.runningapp.database.RunningAppRepository;
import com.example.runningapp.database.entity.Location;

import java.util.List;

// The ViewModel provides data to the UI and survives configuration changes.
// A ViewModel acts as a communication center between the Repository and the UI.
// You can also use a ViewModel to share data between fragments.
// Separating the app's UI data from the Location and Fragment classes lets you better follow the single responsibility principle:
// Your activities are responsible for drawing data to the screen, while the ViewModel can take care of holding and processing all the data needed for the UI.
public class LocationViewModel extends AndroidViewModel {
    private RunningAppRepository repository;
    private LiveData<List<Location>> allLocations;

    public LocationViewModel(@NonNull Application application) {
        super(application);
        repository = new RunningAppRepository(application);
        allLocations = repository.getAllLocations();
    }

    public void insert(Location location) {
        repository.insert(location);
    }

    public void update(Location location) {
        repository.update(location);
    }

    public void delete(Location location) {
        repository.delete(location);
    }

    public LiveData<List<Location>> getLocations(long activityId) {
        return repository.getLocations(activityId);
    }

    public LiveData<List<Location>> getAllLocations() {
        return allLocations;
    }

}
