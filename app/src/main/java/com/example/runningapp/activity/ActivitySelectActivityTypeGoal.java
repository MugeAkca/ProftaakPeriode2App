package com.example.runningapp.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runningapp.R;
import com.example.runningapp.adapter.ActivityTypeAdapter;
import com.example.runningapp.database.entity.ActivityType;
import com.example.runningapp.database.entity.Goal;
import com.example.runningapp.fragment.GoalFragment;
import com.example.runningapp.viewmodel.ActivityTypeViewModel;
import com.example.runningapp.viewmodel.GoalViewModel;

import java.util.List;

import static com.example.runningapp.activity.ActivityGoalNewEdit.GOAL_NEW_EDIT_ACTIVITY_TYPE_ID;
import static com.example.runningapp.activity.ActivityGoalNewEdit.GOAL_NEW_EDIT_ACTIVITY_TYPE_NAME;
import static com.example.runningapp.activity.ActivityGoalNewEdit.GOAL_NEW_EDIT_ID;
import static com.example.runningapp.activity.ActivityGoalNewEdit.GOAL_NEW_EDIT_SPEED_GOAL;
import static com.example.runningapp.activity.ActivityGoalNewEdit.GOAL_NEW_EDIT_TIME_GOAL;
import static com.example.runningapp.fragment.GoalFragment.ADD_GOAL_REQUEST;
import static com.example.runningapp.fragment.GoalFragment.EDIT_GOAL_REQUEST;

public class ActivitySelectActivityTypeGoal extends AppCompatActivity {

    private ActivityTypeViewModel activityTypeViewModel;

    public static final String SELECT_ACTIVITY_TYPE_GOAL_ID = "SELECT_ACTIVITY_TYPE_GOAL_ID";
    private RecyclerView recyclerView;
    private String activityTypeId;
    private String timeGoal;
    private String speedGoal;
    private Goal goal;
    private GoalViewModel goalViewModel;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_type_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ActivityTypeAdapter adapter = new  ActivityTypeAdapter();
        recyclerView.setAdapter(adapter);

        activityTypeViewModel = ViewModelProviders.of(this).get(ActivityTypeViewModel.class);
        activityTypeViewModel.getAllActivityTypes().observe(this, new Observer<List<ActivityType>>() {
            @Override
            public void onChanged(List<ActivityType> activityTypes) {
                adapter.submitList(activityTypes);
            }
        });

        goalViewModel = ViewModelProviders.of(this).get(GoalViewModel.class);

        adapter.setOnItemClickListener(new  ActivityTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ActivityType activityType) {
                Intent intent = new Intent(ActivitySelectActivityTypeGoal.this, ActivityGoalNewEdit.class);
                intent.putExtra(SELECT_ACTIVITY_TYPE_GOAL_ID, String.valueOf(activityType.getType_id()));
                intent.putExtra(GOAL_NEW_EDIT_ACTIVITY_TYPE_NAME, activityType.getName());
                startActivityForResult(intent, ADD_GOAL_REQUEST);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_GOAL_REQUEST && resultCode == RESULT_OK) {
            activityTypeId = data.getStringExtra(GOAL_NEW_EDIT_ACTIVITY_TYPE_ID);
            timeGoal = data.getStringExtra(GOAL_NEW_EDIT_TIME_GOAL);
            speedGoal = data.getStringExtra(GOAL_NEW_EDIT_SPEED_GOAL);

            goal = new Goal(activityTypeId, timeGoal, speedGoal);
            goalViewModel.insert(goal);

            intent = new Intent(ActivitySelectActivityTypeGoal.this, GoalFragment.class);
            startActivity(intent);

        } else if (requestCode == EDIT_GOAL_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(GOAL_NEW_EDIT_ID, -1);

            if (id == -1) {
                return;
            }

            activityTypeId = data.getStringExtra(GOAL_NEW_EDIT_ACTIVITY_TYPE_ID);
            timeGoal = data.getStringExtra(GOAL_NEW_EDIT_TIME_GOAL);
            speedGoal = data.getStringExtra(GOAL_NEW_EDIT_SPEED_GOAL);

            goal = new Goal(activityTypeId, timeGoal, speedGoal);
            goal.setId(id);
            goalViewModel.update(goal);

        }
    }


}
