package com.example.runningapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<String>> stringList;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }


}
