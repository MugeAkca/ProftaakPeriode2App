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
import androidx.lifecycle.ViewModelProviders;

import com.example.runningapp.R;
import com.example.runningapp.database.entity.Location;
import com.example.runningapp.viewmodel.LocationViewModel;

public class ActivityLocationNewEdit extends AppCompatActivity {
    public static final String LOCATION_NEW_EDIT_ID = "LOCATION_NEW_EDIT_ID";
    public static final String LOCATION_ACTIVITY_TYPE_ID = "LOCATION_ACTIVITY_TYPE_ID";
    public static final String LOCATION_LONGITUDE = "LOCATION_LONGITUDE";
    public static final String LOCATION_LATITUDE = "LOCATION_LATITUDE";
    public static final String LOCATION_TIME = "LOCATION_TIME";

    private EditText editTextActivityId;
    private EditText editTextLongitude;
    private EditText editTextLatitude;
    private EditText editTextTime;
    private LocationViewModel locationViewModel;
    private int activityId;
    private String longitude;
    private String latitude;
    private String time;
    private Location location;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        editTextActivityId = findViewById(R.id.edit_activity_type);
        editTextLongitude = findViewById(R.id.edit_longitude);
        editTextLatitude = findViewById(R.id.edit_latitude);
        editTextTime = findViewById(R.id.edit_time);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        final Intent intent = getIntent();

        if (intent.hasExtra(LOCATION_NEW_EDIT_ID)) {
            setTitle("Edit Location");
            editTextActivityId.setText(intent.getStringExtra(LOCATION_ACTIVITY_TYPE_ID));
            editTextLongitude.setText(intent.getStringExtra(LOCATION_NEW_EDIT_ID));
            editTextLatitude.setText(intent.getStringExtra(LOCATION_LATITUDE));
            editTextTime.setText(intent.getStringExtra(LOCATION_TIME));
        } else {
            setTitle("Add Location");
            editTextActivityId.setText(intent.getStringExtra(LOCATION_ACTIVITY_TYPE_ID));
        }
    }

    private void saveLocation() {
        activityId = Integer.valueOf(editTextActivityId.getText().toString());
        longitude = editTextLongitude.getText().toString();
        latitude = editTextLatitude.getText().toString();
        time = editTextTime.getText().toString();

        if (longitude.trim().isEmpty() || latitude.trim().isEmpty()) {
            Toast.makeText(this, "Fill location activity, longitude and langitude", Toast.LENGTH_SHORT).show();
            return;
        }

        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        location = new Location(activityId, longitude, latitude,time);
        locationViewModel.insert(location);
        intent = new Intent(ActivityLocationNewEdit.this, MainActivity.class);
        startActivity(intent);
    }

    // Save location
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_activity, menu);
        return true;
    }

    // save Location
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_activity_type) {
            saveLocation();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


