package com.example.runningapp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.runningapp.R;
import com.example.runningapp.database.entity.Goal;
import com.example.runningapp.viewmodel.GoalViewModel;

import static com.example.runningapp.activity.ActivitySelectActivityTypeGoal.SELECT_ACTIVITY_TYPE_GOAL_ID;


public class ActivityGoalNewEdit extends AppCompatActivity {
    public static final String GOAL_NEW_EDIT_ID = "GOAL_NEW_EDIT_ID";
    public static final String GOAL_NEW_EDIT_ACTIVITY_TYPE_ID = "GOAL_NEW_EDIT_ACTIVITY_TYPE_ID";
    public static final String GOAL_NEW_EDIT_ACTIVITY_TYPE_NAME = "GOAL_NEW_EDIT_ACTIVITY_TYPE_NAME";
    public static final String GOAL_NEW_EDIT_TIME_GOAL = "GOAL_NEW_EDIT_TIME_GOAL";
    public static final String GOAL_NEW_EDIT_SPEED_GOAL = "GOAL_NEW_EDIT_SPEED_GOAL";
    public static final String TEST = "TEST";

    private EditText editTextActivityType;
    private EditText editTextTimeGoal;
    private EditText editTextSpeedGoal;
    private GoalViewModel goalViewModel;
    private Goal goal;
    private String activityTypeId;
    private String timeGoal;
    private String speedGoal;
    private String activityType;
    private Intent getIntent;
    private Intent activityTypeIntent;
    private Intent data;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);

        editTextActivityType = findViewById(R.id.edit_activity_type);
        editTextTimeGoal = findViewById(R.id.edit_time_goal);
        editTextSpeedGoal = findViewById(R.id.edit_speed_goal);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        getIntent = getIntent();

        // Goal activity exists ? edit : add
        if (getIntent.hasExtra(GOAL_NEW_EDIT_ID)) {
            setTitle("Edit Goal");
            editTextActivityType.setText(getIntent.getStringExtra(GOAL_NEW_EDIT_ACTIVITY_TYPE_NAME));
            editTextTimeGoal.setText(getIntent.getStringExtra(GOAL_NEW_EDIT_TIME_GOAL));
            editTextSpeedGoal.setText(getIntent.getStringExtra(GOAL_NEW_EDIT_SPEED_GOAL));
        } else {
            setTitle("Add Goal");
            editTextActivityType.setText(getIntent.getStringExtra(GOAL_NEW_EDIT_ACTIVITY_TYPE_NAME));
        }
    }

    // Save Goal
    // TODO: ?
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void saveGoal() {

        // Get activitytype intent
        Intent activityTypeIntent = getIntent();
        activityTypeId = activityTypeIntent.getStringExtra(SELECT_ACTIVITY_TYPE_GOAL_ID);
        timeGoal = editTextTimeGoal.getText().toString();
        speedGoal = editTextSpeedGoal.getText().toString();

        if (timeGoal.trim().isEmpty() || speedGoal.trim().isEmpty()) {
            Toast.makeText(this, "Fill activity type, time goal and speed goal in", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(GOAL_NEW_EDIT_TIME_GOAL, timeGoal);
        data.putExtra(GOAL_NEW_EDIT_SPEED_GOAL, speedGoal);
        data.putExtra(GOAL_NEW_EDIT_ACTIVITY_TYPE_ID, activityTypeId);

        int id = getIntent().getIntExtra(GOAL_NEW_EDIT_ID, -1);
        if (id != -1) {
            data.putExtra(GOAL_NEW_EDIT_ID, id);
        }


        setResult(RESULT_OK, data);
        finish();
    }


    // User clicked on save goal button?
    //TODO ?
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_goal) {
            saveGoal();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Save button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_goal, menu);
        return true;
    }

}


