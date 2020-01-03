package com.example.runningapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runningapp.R;
import com.example.runningapp.database.entity.ActivityType;

import java.util.ArrayList;
import java.util.List;

public class AddEditStatisticActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "EXTRA_ID";
    public static final String EXTRA_ACTIVITY_TYPE_ID =
            "EXTRA_ACTIVITY_TYPE_ID";
    public static final String START_DATE =
            "START_DATE";
    public static final String END_DATE =
            "END_DATE";
    public static final String ACTIVITY_TYPES = "ACTIVITY_TYPES";

    private EditText editTextActivityType;
    private EditText editTextStartDate;
    private EditText editTextEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);

        editTextActivityType = findViewById(R.id.edit_activity_type);
        editTextStartDate = findViewById(R.id.edit_start_date);
        editTextEndDate = findViewById(R.id.edit_end_date);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Activity");
            editTextActivityType.setText(intent.getStringExtra(EXTRA_ACTIVITY_TYPE_ID));
            editTextStartDate.setText(intent.getStringExtra(START_DATE));
            editTextEndDate.setText(intent.getStringExtra(END_DATE));
        }else {
            setTitle("Add Activity");
        }
    }

    private void saveActivity() {
        String activityTypeId = editTextActivityType.getText().toString();
        String timeActivity = editTextStartDate.getText().toString();
        String speedActivity = editTextEndDate.getText().toString();

        if (activityTypeId.trim().isEmpty()|| timeActivity.trim().isEmpty() || speedActivity.trim().isEmpty()) {
            Toast.makeText(this, "Vul een activiteit, tijd of Activity in", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_ACTIVITY_TYPE_ID, activityTypeId);
        data.putExtra(START_DATE, timeActivity);
        data.putExtra(END_DATE, speedActivity);

        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if (id != -1){
            data.putExtra(EXTRA_ID,id);
        }

        setResult(RESULT_OK, data);
        finish();
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


