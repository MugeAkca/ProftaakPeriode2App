package com.example.runningapp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.print.PrinterId;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runningapp.R;

import static com.example.runningapp.activity.ActivitySelectActivityTypeGoal.SELECT_ACTIVITY_TYPE_GOAL_ID;


public class ActivityTypeNewEdit extends AppCompatActivity {
    public static final String ACTIVITY_TYPE_NEW_EDIT_ID = "ACTIVITY_TYPE_NEW_EDIT_ID";
    public static final String ACTIVITY_TYPE_NEW_EDIT_NAME = "ACTIVITY_TYPE_NEW_EDIT_NAME";

    private EditText editTextActivityType;
    private Intent intent;
    private Intent data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity_type_new_edit);

        editTextActivityType = findViewById(R.id.edit_activity_type_name);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        intent = getIntent();

        if (intent.hasExtra(ACTIVITY_TYPE_NEW_EDIT_ID)) {
            setTitle("Edit ActivityType");
            editTextActivityType.setText(intent.getStringExtra(ACTIVITY_TYPE_NEW_EDIT_NAME));
        } else {
            setTitle("Add ActivityType");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void saveActivityType() {
        String activityTypeName = editTextActivityType.getText().toString();

        if (activityTypeName.trim().isEmpty()) {
            Toast.makeText(this, "Vul een activiteit, tijd of activityType in", Toast.LENGTH_SHORT).show();
            return;
        }

        data = new Intent();
        data.putExtra(ACTIVITY_TYPE_NEW_EDIT_NAME, activityTypeName);

        int id = getIntent().getIntExtra(ACTIVITY_TYPE_NEW_EDIT_ID, -1);
        if (id != -1) {
            data.putExtra(ACTIVITY_TYPE_NEW_EDIT_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_activity_type, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_activity_type) {
            saveActivityType();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


