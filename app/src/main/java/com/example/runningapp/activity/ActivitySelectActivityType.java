package com.example.runningapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runningapp.R;
import com.example.runningapp.adapter.ActivityTypeAdapter;
import com.example.runningapp.database.entity.ActivityType;
import com.example.runningapp.viewmodel.ActivityTypeViewModel;

import java.util.List;

import static com.example.runningapp.activity.ActivityEndActivity.ACTIVITY_START_TIME;
import static com.example.runningapp.activity.ActivityTypeNewEdit.ACTIVITY_TYPE_NEW_EDIT_NAME;
import static com.example.runningapp.activity.ActivityTypeNewEdit.ACTIVITY_TYPE_NEW_EDIT_ID;

public class ActivitySelectActivityType extends AppCompatActivity {

    //TODO ?
    public static final int SELECT_ACTIVITY_TYPE_REQUEST = 2;

    private ActivityTypeViewModel activityTypeViewModel;
    private RecyclerView recyclerView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_type_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ActivityTypeAdapter adapter = new ActivityTypeAdapter();
        recyclerView.setAdapter(adapter);

        activityTypeViewModel = ViewModelProviders.of(this).get(ActivityTypeViewModel.class);
        activityTypeViewModel.getAllActivityTypes().observe(this, new Observer<List<ActivityType>>() {
            @Override
            public void onChanged(List<ActivityType> activityTypes) {
                adapter.submitList(activityTypes);
            }
        });


        intent = getIntent();
        final String startTime = intent.getStringExtra("ACTIVITY_START_TIME");

        adapter.setOnItemClickListener(new ActivityTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ActivityType activityType) {
                Intent intent = new Intent(ActivitySelectActivityType.this, ActivityStartActivity.class);
                intent.putExtra(ACTIVITY_TYPE_NEW_EDIT_ID, String.valueOf(activityType.getType_id()));
                intent.putExtra(ACTIVITY_TYPE_NEW_EDIT_NAME, activityType.getName());
                intent.putExtra(ACTIVITY_START_TIME, startTime);
                startActivityForResult(intent, SELECT_ACTIVITY_TYPE_REQUEST);
            }
        });
    }
}