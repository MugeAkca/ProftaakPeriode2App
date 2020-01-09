package com.example.runningapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.runningapp.R;
import com.example.runningapp.adapter.ActivityAdapter;
import com.example.runningapp.adapter.ActivityTypeAdapter;
import com.example.runningapp.database.entity.Activity;
import com.example.runningapp.database.entity.ActivityType;
import com.example.runningapp.database.entity.Goal;
import com.example.runningapp.viewmodel.ActivityTypeViewModel;
import com.example.runningapp.viewmodel.ActivityViewModel;
import com.example.runningapp.viewmodel.GoalViewModel;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class ActivityActivityNewEdit extends AppCompatActivity {
    public static final String EXTRA_ID =
            "EXTRA_ID";
    public static final String EXTRA_ACTIVITY_TYPE_ID =
            "EXTRA_ACTIVITY_TYPE_ID";
    public static final String START_DATE =
            "START_DATE";
    public static final String END_DATE =
            "END_DATE";

    private EditText editTextActivityType;
    private EditText editTextStartDate;
    private EditText editTextEndDate;
    private ActivityViewModel activityViewModel;
    private ActivityTypeViewModel activityTypeViewModel;


    ArrayList<List<ActivityType>> activityType = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);

        editTextActivityType = findViewById(R.id.edit_activity_type);
        editTextStartDate = findViewById(R.id.edit_start_date);
        editTextEndDate = findViewById(R.id.edit_end_date);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        final Intent intent = getIntent();



        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Activity");
            activityTypeViewModel = ViewModelProviders.of(this).get(ActivityTypeViewModel.class);
            activityTypeViewModel.getAllActivityTypes().observe(this, new Observer<List<ActivityType>>() {
                @Override
                public void onChanged(List<ActivityType> activityTypes) {
                    activityType.add(activityTypes);
                    for(ActivityType activityType : activityTypes){
                        if(activityType.getType_id() == Integer.valueOf(intent.getStringExtra(EXTRA_ACTIVITY_TYPE_ID))){
                            editTextActivityType.setText(activityType.getName());
                        }
                    }
                }
            });
            editTextStartDate.setText(intent.getStringExtra(START_DATE));
            editTextEndDate.setText(intent.getStringExtra(END_DATE));
        } else {
            setTitle("Add Activity");
            editTextActivityType.setText(intent.getStringExtra(EXTRA_ACTIVITY_TYPE_ID));
        }
    }

    private void saveActivity() {
        String activityTypeId = editTextActivityType.getText().toString();
        String timeActivity = editTextStartDate.getText().toString();
        String speedActivity = editTextEndDate.getText().toString();


        if (activityTypeId.trim().isEmpty() || timeActivity.trim().isEmpty() || speedActivity.trim().isEmpty()) {
            Toast.makeText(this, "Vul een activiteit, tijd of Activity in", Toast.LENGTH_SHORT).show();
            return;
        }

        activityViewModel = ViewModelProviders.of(this).get(ActivityViewModel.class);


        Activity activity = new Activity(activityTypeId, timeActivity, speedActivity);
        activityViewModel.insert(activity);

        Intent intent = new Intent(ActivityActivityNewEdit.this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_activity_type) {
            saveActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


