package com.example.runningapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.runningapp.database.RunningAppRepository;
import com.example.runningapp.database.entity.Category;

import java.util.List;

// The ViewModel provides data to the UI and survives configuration changes.
// A ViewModel acts as a communication center between the Repository and the UI.
// You can also use a ViewModel to share data between fragments.
// Separating your app's UI data from your Activity and Fragment classes lets you better follow the single responsibility principle:
// Your activities are responsible for drawing data to the screen, while your ViewModel can take care of holding and processing all the data needed for the UI.
// store and proces data ui and communicate with model
// data doesnt get lost config changes viewmodel only removed memory as lifecycle activity is over (activity finhised or detached)
// never store or reference activity because the viewmodel is designed to outlive activity after it is destroyed (memory leaks)
// We exctend viewmodel we can pass data down to the database
public class CategoryViewModel extends AndroidViewModel {
    private RunningAppRepository repository;
    private LiveData<List<Category>> allCategorys;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        repository = new RunningAppRepository(application);
        allCategorys = repository.getAllCategories();
    }

    public void insert(Category category) {
        repository.insert(category);
    }

    public void update(Category category) {
        repository.update(category);
    }

    public void delete(Category category) {
        repository.delete(category);
    }

    public void deleteAllCategorys(Category category) {
        repository.deleteAll(category);
    }

    public LiveData<List<Category>> getAllCategorys() {
        return allCategorys;
    }
}