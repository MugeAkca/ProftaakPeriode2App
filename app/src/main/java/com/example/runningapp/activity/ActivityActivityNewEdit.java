package com.example.runningapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.runningapp.R;
import com.example.runningapp.database.entity.Activity;
import com.example.runningapp.database.entity.Location;
import com.example.runningapp.viewmodel.ActivityViewModel;
import com.example.runningapp.viewmodel.LocationViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;


public class ActivityActivityNewEdit extends AppCompatActivity implements OnMapReadyCallback {

    public static final String ACTIVITY_NEW_EDIT_ID = "ACTIVITY_NEW_EDIT_ID";
    public static final String ACTIVITY_NEW_EDIT_TYPE_ID = "ACTIVITY_NEW_EDIT_TYPE_ID";
    public static final String ACTIVITY_NEW_EDIT_TYPE_NAME = "ACTIVITY_NEW_EDIT_TYPE_NAME";
    public static final String ACTIVITY_NEW_EDIT_START_DATE = "ACTIVITY_NEW_EDIT_START_DATE";
    public static final String ACTIVITY_NEW_EDIT_END_DATE = "ACTIVITY_NEW_EDIT_END_DATE";

    private TextView editTextActivityType;
    private TextView editTextStartDate;
    private TextView editTextEndDate;
    private ActivityViewModel activityViewModel;
    private LocationViewModel locationViewModel;
    private String activityTypeId;
    private String timeActivity;
    private String speedActivity;
    private String activityNewEditId;
    private String pattern = "dd-MMM-yyyy HH:mm:ss";
    private ArrayList<Location> locations = new ArrayList<>();
    private List<LatLng> routePoints;
    private GoogleMap gmap;


    private Polyline route;
    private PolylineOptions routeOps;


    private android.location.Location currentLocation;
    private com.example.runningapp.database.entity.Location saveLocation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);

        editTextActivityType = findViewById(R.id.edit_activity_type);
        editTextStartDate = findViewById(R.id.edit_start_date);
        editTextEndDate = findViewById(R.id.edit_end_date);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        final Intent intent = getIntent();

        activityNewEditId = intent.getStringExtra(ACTIVITY_NEW_EDIT_ID);

        // Activity exists?
            setTitle("Activity");

            // Load location
//            RecyclerView recyclerView = findViewById(R.id.recycler_view_location);
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
//            recyclerView.setHasFixedSize(true);
//            final LocationAdapter adapterLocation = new LocationAdapter();
//            recyclerView.setAdapter(adapterLocation);
//
            locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
//            locationViewModel.getLocations(Long.valueOf(activityNewEditId)).observe(this, new Observer<List<Location>>() {
//
//                @Override
//                public void onChanged(List<Location> locations) {
//                    adapterLocation.submitList(locations);
//                }
//            });

            // Set TextEdits
            editTextActivityType.setText(intent.getStringExtra(ACTIVITY_NEW_EDIT_TYPE_NAME));
            editTextStartDate.setText(intent.getStringExtra(ACTIVITY_NEW_EDIT_START_DATE));
            editTextEndDate.setText(intent.getStringExtra(ACTIVITY_NEW_EDIT_END_DATE));

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.activityMap);
            mapFragment.getMapAsync(this);
    }

    // Save activity
    private void saveActivity() {
        activityTypeId = editTextActivityType.getText().toString();
        timeActivity = editTextStartDate.getText().toString();
        speedActivity = editTextEndDate.getText().toString();

        if (activityTypeId.trim().isEmpty() || timeActivity.trim().isEmpty() || speedActivity.trim().isEmpty()) {
            Toast.makeText(this, "Check activity, start and end date", Toast.LENGTH_SHORT).show();
            return;
        }

        // Does activityViewModel exists ? get viewmodel : initiate viewmodel
        activityViewModel = ViewModelProviders.of(this).get(ActivityViewModel.class);

        Activity activity = new Activity(activityTypeId, timeActivity, speedActivity);

        activityViewModel.insert(activity);

        Intent intent = new Intent(ActivityActivityNewEdit.this, MainActivity.class);
        startActivity(intent);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMyLocationEnabled(true);
        gmap.getUiSettings().setMyLocationButtonEnabled(true);

        List<Location> getLocations = locationViewModel.getLocationsList(Long.valueOf(activityNewEditId));

        routeOps = new PolylineOptions()
                .color(Color.BLUE)
                .width(4);
        route = gmap.addPolyline(routeOps);
        route.setVisible(true);

        for(Location locationNow : getLocations) {
            onLocationChanged(locationNow.getLatitude(), locationNow.getLongitude());
        }

        LatLng latLng = new LatLng(getLocations.get(0).getLatitude(), getLocations.get(0).getLongitude());
        Log.d("test", latLng.toString());
        gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8));

    }

    public void onLocationChanged(double l1, double l2) {

        try {

            LatLng latLng = new LatLng(l1, l2);

            routePoints = route.getPoints();
            routePoints.add(latLng);
            route.setPoints(routePoints);

            gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8));
        }catch (NullPointerException e){
            Log.d("locationchangedexceptio", String.valueOf(e));
        }
    }


    // add save button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Intent intent = getIntent();
        if(!intent.hasExtra(ACTIVITY_NEW_EDIT_ID)) {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.menu_add_activity, menu);
        }
        return true;
    }

    // after click save activity
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_activity_type) {
            saveActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
////        mMap = googleMap;
////
////        // Add a marker in Sydney, Australia, and move the camera.
////        LatLng sydney = new LatLng(-34, 151);
////        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
////        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//    }
}

