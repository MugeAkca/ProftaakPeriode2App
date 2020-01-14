package com.example.runningapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import com.example.runningapp.R;
import com.example.runningapp.activity.ActivityEndActivity;
import com.example.runningapp.database.entity.Activity;
import com.example.runningapp.viewmodel.ActivityTypeViewModel;
import com.example.runningapp.viewmodel.ActivityViewModel;
import com.example.runningapp.viewmodel.LocationViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import static com.example.runningapp.activity.ActivityTypeNewEdit.ACTIVITY_TYPE_NEW_EDIT_ID;
import static com.example.runningapp.activity.ActivityTypeNewEdit.ACTIVITY_TYPE_NEW_EDIT_NAME;
import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;


public class ActivityStartActivity extends FragmentActivity implements OnMapReadyCallback {
    //Clock
    Chronometer timer;

    //Map
    private MapView mapView;
    private GoogleMap gmap;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private static final int PERMISSION_ID = 9001;

    private Location currentLocation;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private boolean requestingLocationUpdates;

    private Polyline route;
    private PolylineOptions routeOps;
    private List<LatLng> routePoints;
    private LocationViewModel locationViewModel;
    private Intent intent;
    private com.example.runningapp.database.entity.Location saveLocation;
    private String activityTypeName;
    private String activityStartTime;
    private SimpleDateFormat simpleDateFormat;
    private Date date;
    private String activityTypeId;
    private ActivityViewModel activityViewModel;
    private long activityId;
    private Button btnEndActivity;
    private Looper myLooper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_activity_started);

            //Clock
            timer = findViewById(R.id.chronometer);
            timer.setBase(SystemClock.elapsedRealtime());
            timer.setFormat("H:MM:SS");
            timer.start();

            timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                @Override
                public void onChronometerTick(Chronometer chronometer) {
                    //Log.d("CHM", "onChronometerTick: "+ chronometer.getText());
                }
            });

            intent = getIntent();
            activityTypeId = intent.getStringExtra("ACTIVITY_TYPE_NEW_EDIT_ID");
            activityTypeName = intent.getStringExtra("ACTIVITY_TYPE_NEW_EDIT_NAME");
            activityStartTime = intent.getStringExtra("ACTIVITY_START_TIME");

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("DD-MM-yyyy HH:mm");
            date = new java.util.Date();
            final String ts = simpleDateFormat.format(date);

            activityViewModel = ViewModelProviders.of(this).get(ActivityViewModel.class);
            Activity activity = new Activity(activityTypeId, activityStartTime, ts);
            activityId = activityViewModel.insert(activity);

            //Set OnClickListener on the button for when the user stops the activity
            Button btnEndActivity = findViewById(R.id.btnEndActivity);
            btnEndActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), ActivityEndActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            //Map
            if(!checkPermissions()) {
                requestPermissions();
            }
            Log.d("GPS", "OnCreate: GPS ");

            fusedLocationProviderClient = getFusedLocationProviderClient(this);



            getLastLocation();
            startLocationUpdates();


        } catch (NumberFormatException | NullPointerException e){

        }

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
            super.onStop();
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }
    @Override
    protected void onPause() {
        try {
            super.onPause();
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);

        }catch(NullPointerException e){

        }
    }
    @Override
    protected void onDestroy() {
        try {
            super.onDestroy();
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }catch (NullPointerException e){

        }
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMyLocationEnabled(true);
        gmap.getUiSettings().setMyLocationButtonEnabled(true);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                Log.d("GPS", "callback");
                onLocationChanged(locationResult.getLastLocation());
            }
        };

        routeOps = new PolylineOptions()
                .color(Color.BLUE)
                .width(4);
        route = gmap.addPolyline(routeOps);
        route.setVisible(true);

        if(currentLocation != null) {
            LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        }
    }

    // Trigger new location updates at interval
    protected void startLocationUpdates() {

        try {

            // Create the location request to start receiving updates
            locationRequest = new LocationRequest();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(2000);



            // Create LocationSettingsRequest object using location request
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
            builder.addLocationRequest(locationRequest);
            LocationSettingsRequest locationSettingsRequest = builder.build();

            // Check whether location settings are satisfied
            // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
            SettingsClient settingsClient = LocationServices.getSettingsClient(this);
            settingsClient.checkLocationSettings(locationSettingsRequest);

            // new Google API SDK v11 uses getFusedLocationProviderClient(this)
            getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest, new LocationCallback() {
                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                            // do work here
                            onLocationChanged(locationResult.getLastLocation());

                        }
                    },
                    Looper.myLooper());


        }catch (NullPointerException e){

        }
    }

    public void onLocationChanged(Location location) {

        try {

            // You can now create a LatLng Object for use with maps
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            currentLocation = location;

            routePoints = route.getPoints();
            routePoints.add(latLng);
            route.setPoints(routePoints);

            gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

            locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
            saveLocation = new com.example.runningapp.database.entity.Location(activityId, location.getLongitude(), location.getLatitude(), location.getTime());
            locationViewModel.insert(saveLocation);

//            Log.d("GPS", "Longitude: " + currentLocation.getLongitude() + " || Latitude:" + currentLocation.getLatitude());

        }catch (NullPointerException e){

        }
    }


    private void getLastLocation() {
        if(checkPermissions())
        {
            Log.d("GPS", "GetLastLocation");

            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        currentLocation = location;
                        Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.activityMap);
                        assert supportMapFragment != null;
                        supportMapFragment.getMapAsync(ActivityStartActivity.this);

                        Log.d("GPS", "gps: "+ currentLocation.getLongitude());
                    }
                }
            });
        }
        else{
            requestPermissions();
        }

    }




    //    private void startLocationUpdates() {
//        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
//                locationCallback,
//                Looper.getMainLooper());
//        requestingLocationUpdates = true;
//    }
    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        requestingLocationUpdates = false;
    }

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Granted. Start getting the location information
            }
        }
    }

}