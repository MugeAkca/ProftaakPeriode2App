package com.example.runningapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.runningapp.R;
import com.example.runningapp.database.entity.Activity;
import com.example.runningapp.viewmodel.ActivityViewModel;


public class ActivityEndActivity extends AppCompatActivity {
    private static final int ACTION = 1;
    public static final String START_TIME = "START_TIME";
    static final String END_TIME = "END_TIME";
    ActivityViewModel activityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_end);

        Intent intent = getIntent();
        final String startTime = intent.getStringExtra(START_TIME);
        final String endTime = intent.getStringExtra(END_TIME);

        activityViewModel = ViewModelProviders.of(this).get(ActivityViewModel.class);

        Button btnCancelActivity = findViewById(R.id.btnCancelActivity);
        btnCancelActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button btnSaveActivity = findViewById(R.id.btnSaveActivityResult);
        btnSaveActivity.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Activity activity = new Activity("1", startTime, endTime);
                activityViewModel.insert(activity);

                Intent intent = new Intent(ActivityEndActivity.this, MainActivity.class);
                startActivityForResult(intent, ACTION);
            }
        });
    }
}
