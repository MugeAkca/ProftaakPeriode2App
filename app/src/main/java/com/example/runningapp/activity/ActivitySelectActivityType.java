package com.example.runningapp.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runningapp.R;
import com.example.runningapp.adapter.ActivityTypeAdapter;
import com.example.runningapp.database.entity.ActivityType;
import com.example.runningapp.viewmodel.ActivityTypeViewModel;

import java.util.List;

import static com.example.runningapp.activity.ActivityEndActivity.START_TIME;
import static com.example.runningapp.activity.ActivityTypeNewEdit.EXTRA_ACTIVITY_TYPE_NAME;
import static com.example.runningapp.activity.ActivityTypeNewEdit.EXTRA_ID;

public class ActivitySelectActivityType  extends AppCompatActivity {

    public static final int EDIT_NOTE_REQUEST = 2;

    private ActivityTypeViewModel activityTypeViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_type_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ActivityTypeAdapter adapter = new  ActivityTypeAdapter();
        recyclerView.setAdapter(adapter);

        activityTypeViewModel = ViewModelProviders.of(this).get(ActivityTypeViewModel.class);
        activityTypeViewModel.getAllActivityTypes().observe(this, new Observer<List<ActivityType>>() {
            @Override
            public void onChanged(List<ActivityType> activityTypes) {
                adapter.submitList(activityTypes);
            }
        });



        Intent intent = getIntent();
        final String startTime = intent.getStringExtra("START_TIME");

        adapter.setOnItemClickListener(new  ActivityTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ActivityType activityType) {
                Intent intent = new Intent(ActivitySelectActivityType.this, ActivityStartActivity.class);
                intent.putExtra(EXTRA_ID, activityType.getType_id());
                intent.putExtra(EXTRA_ACTIVITY_TYPE_NAME, activityType.getName());
                intent.putExtra(START_TIME, startTime);
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }
}