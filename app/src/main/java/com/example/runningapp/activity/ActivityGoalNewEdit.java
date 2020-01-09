package com.example.runningapp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.runningapp.R;
import com.example.runningapp.database.entity.Activity;
import com.example.runningapp.database.entity.Goal;
import com.example.runningapp.fragment.GoalFragment;
import com.example.runningapp.viewmodel.ActivityViewModel;
import com.example.runningapp.viewmodel.GoalViewModel;

import static com.example.runningapp.activity.ActivitySelectActivityTypeGoal.EXTRA_ACTIVITY_TYPE_ID2;
import static com.example.runningapp.activity.ActivityTypeNewEdit.EXTRA_ACTIVITY_TYPE_NAME;


public class ActivityGoalNewEdit extends AppCompatActivity {
    public static final String EXTRA_ID =
            "EXTRA_ID";
    public static final String EXTRA_ACTIVITY_TYPE_ID =
            "EXTRA_ACTIVITY_TYPE_ID";
    static final String EXTRA_ACTIVITY_TYPE_NAME2 = "EXTRA_ACTIVITY_TYPE_NAME";
    public static final String EXTRA_TIME_GOAL =
            "EXTRA_TIME_GOAL";
    public static final String EXTRA_SPEED_GOAL =
            "EXTRA_SPEED_GOAL";


    private EditText editTextActivityType;
    private EditText editTextTimeGoal;
    private EditText editTextSpeedGoal;
    GoalViewModel goalViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goal);

        editTextActivityType = findViewById(R.id.edit_activity_type);
        editTextTimeGoal = findViewById(R.id.edit_time_goal);
        editTextSpeedGoal = findViewById(R.id.edit_speed_goal);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Goal");
            editTextActivityType.setText(intent.getStringExtra(EXTRA_ACTIVITY_TYPE_ID));
            editTextTimeGoal.setText(intent.getStringExtra(EXTRA_TIME_GOAL));
            editTextSpeedGoal.setText(intent.getStringExtra(EXTRA_SPEED_GOAL));
        }else {
            setTitle("Add Goal");
            editTextActivityType.setText(intent.getStringExtra(EXTRA_ACTIVITY_TYPE_NAME2));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void saveGoal() {
        String activityTypeId = editTextActivityType.getText().toString();
        String timeGoal = editTextTimeGoal.getText().toString();
        String speedGoal = editTextSpeedGoal.getText().toString();

        if (activityTypeId.trim().isEmpty()|| timeGoal.trim().isEmpty() || speedGoal.trim().isEmpty()) {
            Toast.makeText(this, "Vul een activiteit, tijd of goal in", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent activityTypeIntent = getIntent();
        String activityType = activityTypeIntent.getStringExtra(EXTRA_ACTIVITY_TYPE_ID2);

        Intent data = new Intent();
        data.putExtra(EXTRA_ACTIVITY_TYPE_ID, activityTypeIntent.getStringExtra(EXTRA_ACTIVITY_TYPE_ID2));
        data.putExtra(EXTRA_TIME_GOAL, timeGoal);
        data.putExtra(EXTRA_SPEED_GOAL, speedGoal);

        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if (id != -1){
            data.putExtra(EXTRA_ID,id);
        }

        goalViewModel = ViewModelProviders.of(this).get(GoalViewModel.class);

        Goal goal = new Goal(activityType, timeGoal, speedGoal);
        goalViewModel.insert(goal);

        Intent intent = new Intent(ActivityGoalNewEdit.this, MainActivity.class);
        startActivity(intent);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_goal) {
            saveGoal();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_goal, menu);
        return true;
    }

}


