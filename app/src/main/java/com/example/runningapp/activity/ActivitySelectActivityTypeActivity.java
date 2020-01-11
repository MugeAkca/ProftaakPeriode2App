package com.example.runningapp.activity;

import android.content.Intent;
import android.os.Bundle;

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

import static com.example.runningapp.activity.ActivityGoalNewEdit.GOAL_NEW_EDIT_ACTIVITY_TYPE_NAME;
import static com.example.runningapp.fragment.GoalFragment.ADD_GOAL_REQUEST;

public class ActivitySelectActivityTypeActivity extends AppCompatActivity {

    public static final String SELECT_ACTIVITY_TYPE_ID = "SELECT_ACTIVITY_TYPE_ID";

    private ActivityTypeViewModel activityTypeViewModel;
    private RecyclerView recyclerView;

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

        adapter.setOnItemClickListener(new ActivityTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ActivityType activityType) {
                Intent intent = new Intent(ActivitySelectActivityTypeActivity.this, ActivityActivityNewEdit.class);
                intent.putExtra(SELECT_ACTIVITY_TYPE_ID, String.valueOf(activityType.getType_id()));
                intent.putExtra(GOAL_NEW_EDIT_ACTIVITY_TYPE_NAME, activityType.getName());
                startActivityForResult(intent, ADD_GOAL_REQUEST);

            }
        });
    }
}