package com.example.runningapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.runningapp.database.RunningAppRepository;
import com.example.runningapp.database.entity.Goal;

import java.util.List;

// The ViewModel provides data to the UI and survives configuration changes.
// A ViewModel acts as a communication center between the Repository and the UI.
// You can also use a ViewModel to share data between fragments.
// Separating your app's UI data from your Activity and Fragment classes lets you better follow the single responsibility principle:
// Your activities are responsible for drawing data to the screen, while your ViewModel can take care of holding and processing all the data needed for the UI.
public class GoalViewModel extends AndroidViewModel {
    private RunningAppRepository repository;
    private LiveData<List<Goal>> allGoals;

    public GoalViewModel(@NonNull Application application) {
        super(application);
        repository = new RunningAppRepository(application);
        allGoals = repository.getAllGoals();
    }

    public void insert(Goal goal) {
        repository.insertGoal(goal);
    }

    public void update(Goal goal) {
        repository.updateGoal(goal);
    }

    public void delete(Goal goal) {
        repository.deleteGoal(goal);
    }

    public void deleteAllGoals() {
        repository.deleteAllGoals();
    }

    public LiveData<List<Goal>> getAllGoals() {
        return allGoals;
    }
}
