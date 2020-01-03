package com.example.runningapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runningapp.R;
import com.example.runningapp.adapter.ActivityAdapter;
import com.example.runningapp.database.entity.Activity;
import com.example.runningapp.viewmodel.ActivityViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class StatisticActivity extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;


    private ActivityViewModel activityViewModel;
    private Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_main);

        FloatingActionButton buttonAddActivity = findViewById(R.id.button_add_activity);
        buttonAddActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StatisticActivity.this, AddEditStatisticActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ActivityAdapter adapter = new ActivityAdapter();
        recyclerView.setAdapter(adapter);

        activityViewModel = ViewModelProviders.of(this).get(ActivityViewModel.class);
        activityViewModel.getAllActivities().observe(this, new Observer<List<Activity>>() {
            @Override
            public void onChanged(List<Activity> activitys) {
                adapter.submitList(activitys);
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
                activityViewModel.delete(adapter.getActivityAt(viewHolder.getAdapterPosition()));
                Toast.makeText(StatisticActivity.this, "Activity deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ActivityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Activity activity) {
                Intent intent = new Intent(StatisticActivity.this, AddEditStatisticActivity.class);
                intent.putExtra(AddEditStatisticActivity.EXTRA_ID, activity.getId());
                intent.putExtra(AddEditStatisticActivity.EXTRA_ACTIVITY_TYPE_ID, String.valueOf(activity.getActivity_type_id()));
                intent.putExtra(AddEditStatisticActivity.START_DATE, String.valueOf(activity.getStart_date()));
                intent.putExtra(AddEditStatisticActivity.END_DATE, String.valueOf(activity.getEnd_date()));
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String activityActivity = data.getStringExtra(AddEditStatisticActivity.EXTRA_ACTIVITY_TYPE_ID);
            String timeActivity = data.getStringExtra(AddEditStatisticActivity.START_DATE);
            String speedActivity = data.getStringExtra(AddEditStatisticActivity.END_DATE);

            Activity activity = new Activity(activityActivity, timeActivity, speedActivity);
            activityViewModel.insert(activity);

            Toast.makeText(this, "Activity saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditStatisticActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Activity can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String activityType = data.getStringExtra(AddEditStatisticActivity.EXTRA_ACTIVITY_TYPE_ID);
            String timeActivity = data.getStringExtra(AddEditStatisticActivity.START_DATE);
            String speedActivity = data.getStringExtra(AddEditStatisticActivity.END_DATE);

            Activity activity = new Activity(activityType, timeActivity, speedActivity);
            activity.setId(id);
            activityViewModel.update(activity);

            Toast.makeText(this, "Activity Updated", Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(this, "Activity not saved", Toast.LENGTH_SHORT).show();
        }
    }

    // Option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    // Delete item
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all_activities) {
            activityViewModel.deleteAllActivitys(activity);
            Toast.makeText(this, "All activitys deleted", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
