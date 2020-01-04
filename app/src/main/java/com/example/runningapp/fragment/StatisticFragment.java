package com.example.runningapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.example.runningapp.activity.AddEditStatisticActivity;
import com.example.runningapp.adapter.ActivityAdapter;
import com.example.runningapp.database.entity.Activity;
import com.example.runningapp.viewmodel.ActivityViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class StatisticFragment extends Fragment {
    private static final int ADD_NOTE_REQUEST = 1;
    private static final int EDIT_NOTE_REQUEST = 2;


    private ActivityViewModel activityViewModel;
    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_activity_main, container, false);

        FloatingActionButton buttonAddActivity = root.findViewById(R.id.button_add_activity);
        buttonAddActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddEditStatisticActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
                Toast.makeText(getContext(), "Activity deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ActivityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Activity activity) {
                Intent intent = new Intent(getContext(), AddEditStatisticActivity.class);
                intent.putExtra(AddEditStatisticActivity.EXTRA_ID, activity.getId());
                intent.putExtra(AddEditStatisticActivity.EXTRA_ACTIVITY_TYPE_ID, String.valueOf(activity.getActivity_type_id()));
                intent.putExtra(AddEditStatisticActivity.START_DATE, String.valueOf(activity.getStart_date()));
                intent.putExtra(AddEditStatisticActivity.END_DATE, String.valueOf(activity.getEnd_date()));
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String activityActivity = data.getStringExtra(AddEditStatisticActivity.EXTRA_ACTIVITY_TYPE_ID);
            String timeActivity = data.getStringExtra(AddEditStatisticActivity.START_DATE);
            String speedActivity = data.getStringExtra(AddEditStatisticActivity.END_DATE);

            Activity activity = new Activity(activityActivity, timeActivity, speedActivity);
            activityViewModel.insert(activity);

        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditStatisticActivity.EXTRA_ID, -1);

            if (id == -1) {
                return;
            }

            String activityType = data.getStringExtra(AddEditStatisticActivity.EXTRA_ACTIVITY_TYPE_ID);
            String timeActivity = data.getStringExtra(AddEditStatisticActivity.START_DATE);
            String speedActivity = data.getStringExtra(AddEditStatisticActivity.END_DATE);

            Activity activity = new Activity(activityType, timeActivity, speedActivity);
            activity.setId(id);
            activityViewModel.update(activity);



        } else {
        }
    }


    // Delete item
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all_activities) {
            activityViewModel.deleteAllActivitys(activity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

