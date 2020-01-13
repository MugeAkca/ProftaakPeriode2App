package com.example.runningapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Timer;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;


public class ActivityStartActivity extends FragmentActivity implements OnMapReadyCallback, SensorEventListener {
    //Clock
    private Chronometer timer;




    //Map
    private MapView mapView;
    private GoogleMap gmap;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private static final int PERMISSION_ID = 9001;

    private Location currentLocation;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;

    private Polyline route;
    private PolylineOptions routeOps;
    private List<LatLng> routePoints;

    //Step Sensor
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private boolean isSensorPresent = false;
    private TextView mStepsSinceReboot;
    private int stepsSinceReboot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_started);


        //Set OnClickListener on the button for when the user stops the activity
        Button btnEndActivity = findViewById(R.id.btnEndActivity);
        btnEndActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityEndActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        //First check permissions
        if(!checkPermissions()) {
            requestPermissions();
        }


        //Step sensor
        mStepsSinceReboot = findViewById(R.id.step);

        mSensorManager = (SensorManager)this.getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
        {
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isSensorPresent = true;
        }
        else{
            isSensorPresent = false;
        }



        //Clock
        Chronometer simpleChronometer = findViewById(R.id.chronometer); // initiate a chronometer
        simpleChronometer.setFormat("H:MM:SS");
        //Calculate the time for the format hour/minute/seconds
        simpleChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                int h   = (int)(time /3600000);
                int m = (int)(time - h*3600000)/60000;
                int s= (int)(time - h*3600000- m*60000)/1000 ;
                String t = (h < 10 ? "0"+h: h)+":"+(m < 10 ? "0"+m: m)+":"+ (s < 10 ? "0"+s: s);
                chronometer.setText(t);
            }
        });
        simpleChronometer.setBase(SystemClock.elapsedRealtime());
        simpleChronometer.setText("00:00:00");
        simpleChronometer.start(); // start a chronometer

        //Map
        fusedLocationProviderClient = getFusedLocationProviderClient(this);
        getLastLocation();
        startLocationUpdates();

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
        if(isSensorPresent){
            mSensorManager.registerListener(this, mSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
        if(isSensorPresent){
            mSensorManager.unregisterListener(this);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopLocationUpdates();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(googleMap == null){
            return;
        }
        gmap = googleMap;
        gmap.setMyLocationEnabled(true);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                onLocationChanged(locationResult.getLastLocation());
            }
        };

        //Draw a line from where the user starts
        routeOps = new PolylineOptions()
                .color(Color.BLUE)
                .width(4);
        route = gmap.addPolyline(routeOps);
        route.setVisible(true);

        //Zoom into the currentlocation
        if(currentLocation != null) {
            LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        }
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        int calculatedSteps = Math.round(event.values[0]);
        if(stepsSinceReboot  < 1){
            stepsSinceReboot = Math.round(event.values[0]);

        }

        mStepsSinceReboot.setText(Integer.toString(calculatedSteps - stepsSinceReboot));

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    // Trigger new location updates at interval
    public void startLocationUpdates() {

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
    }

    public void stopLocationUpdates(){
        if(locationCallback != null)
        {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    }

    public void onLocationChanged(Location location) {
        // You can now create a LatLng Object for use with maps
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        currentLocation = location;

        if(route != null){
            routePoints = route.getPoints();
            routePoints.add(latLng);
            route.setPoints(routePoints);
        }

        if(gmap != null){
            gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
        }

        Log.d("GPS", "Longitude: "+ currentLocation.getLongitude() + " || Latitude:" + currentLocation.getLatitude());
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

    //Permission check methods
    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACTIVITY_RECOGNITION
                },
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
