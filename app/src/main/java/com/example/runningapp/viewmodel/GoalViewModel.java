package com.example.runningapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.runningapp.database.RunningAppRepository;
import com.example.runningapp.database.entity.Goal;
import com.example.runningapp.database.entity.GoalActivitySubType;

import java.util.List;

// The ViewModel provides data to the UI and survives configuration changes.
// A ViewModel acts as a communication center between the Repository and the UI.
// You can also use a ViewModel to share data between fragments.
// Separating ther app's UI data from ther Activity and Fragment classes lets the better follow the single responsibility principle:
// Your activities are responsible for drawing data to the screen, while ther ViewModel can take care of holding and processing all the data needed for the UI.
public class GoalViewModel extends AndroidViewModel {
    private RunningAppRepository repository;
    private LiveData<List<GoalActivitySubType>> allGoals;
    private Goal goal;

    public GoalViewModel(@NonNull Application application) {
        super(application);
        repository = new RunningAppRepository(application);
        allGoals = repository.getAllGoals();
    }

    public void insert(Goal goal) {
        repository.insert(goal);
    }

    public void update(Goal goal) {
        repository.update(goal);
    }

    public void delete(Goal goal) {
        repository.delete(goal);
    }

    public LiveData<List<GoalActivitySubType>> getAllGoals() {
        return allGoals;
    }
}
