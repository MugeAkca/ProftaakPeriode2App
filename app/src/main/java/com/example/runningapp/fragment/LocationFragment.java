package com.example.runningapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runningapp.R;
import com.example.runningapp.activity.ActivityLocationNewEdit;
import com.example.runningapp.adapter.LocationAdapter;
import com.example.runningapp.database.entity.Location;
import com.example.runningapp.viewmodel.LocationViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.runningapp.activity.ActivityLocationNewEdit.LOCATION_LATITUDE;
import static com.example.runningapp.activity.ActivityLocationNewEdit.LOCATION_TIME;
import static com.example.runningapp.activity.ActivityLocationNewEdit.LOCATION_ACTIVITY_TYPE_ID;
import static com.example.runningapp.activity.ActivityLocationNewEdit.LOCATION_NEW_EDIT_ID;
import static com.example.runningapp.activity.ActivityLocationNewEdit.LOCATION_LONGITUDE;

public class LocationFragment extends Fragment {
    private static final int ADD_LOCATION_REQUEST = 1;
    private static final int EDIT_LOCATION_REQUEST = 2;

    private LocationViewModel locationViewModel;
    private View root;
    private FloatingActionButton buttonAddLocation;
    private RecyclerView recyclerView;
    private Location location;
    private String longitude;
    private String langitude;
    private String time;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_location_main, container, false);

        buttonAddLocation = root.findViewById(R.id.button_add_location);
        buttonAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActivityLocationNewEdit.class);
                startActivityForResult(intent, ADD_LOCATION_REQUEST);
            }
        });

        recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        final LocationAdapter adapter = new LocationAdapter();
        recyclerView.setAdapter(adapter);

        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        locationViewModel.getAllLocations().observe(this, new Observer<List<Location>>() {
            @Override
            public void onChanged(List<Location> locations) {
                adapter.submitList(locations);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                locationViewModel.delete(adapter.getLocationAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(), "Location deleted", Toast.LENGTH_SHORT).show();
            }

        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new LocationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Location location) {
                Intent intent = new Intent(getContext(), ActivityLocationNewEdit.class);
                intent.putExtra(LOCATION_NEW_EDIT_ID, String.valueOf(location.getLocation_id()));
                intent.putExtra(LOCATION_ACTIVITY_TYPE_ID, String.valueOf(location.getActivity_id()));
                intent.putExtra(LOCATION_LONGITUDE, String.valueOf(location.getLongitude()));
                intent.putExtra(LOCATION_LATITUDE, String.valueOf(location.getLatitude()));
                intent.putExtra(LOCATION_TIME, String.valueOf(location.getTime()));
                startActivityForResult(intent, EDIT_LOCATION_REQUEST);
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_LOCATION_REQUEST && resultCode == RESULT_OK) {
            int activityTypeId = Integer.valueOf(data.getStringExtra(LOCATION_ACTIVITY_TYPE_ID));
            longitude = data.getStringExtra(LOCATION_LONGITUDE);
            langitude = data.getStringExtra(LOCATION_LATITUDE);
            time = data.getStringExtra(LOCATION_TIME);
            location = new Location(activityTypeId, longitude, langitude, time);
            locationViewModel.insert(location);

        } else if (requestCode == EDIT_LOCATION_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(LOCATION_NEW_EDIT_ID, -1);

            if (id == -1) {
                return;
            }
        }
    }
}

