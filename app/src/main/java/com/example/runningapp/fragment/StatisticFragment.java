package com.example.runningapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.runningapp.activity.ActivityActivityNewEdit;
import com.example.runningapp.adapter.ActivityAdapter;
import com.example.runningapp.adapter.ActivityCategoryAdapter;
import com.example.runningapp.database.entity.Activity;
import com.example.runningapp.database.entity.ActivityCategory;
import com.example.runningapp.database.entity.ActivityType;
import com.example.runningapp.viewmodel.ActivityCategoryViewModel;
import com.example.runningapp.viewmodel.ActivityTypeViewModel;
import com.example.runningapp.viewmodel.ActivityViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.runningapp.activity.ActivityActivityNewEdit.END_DATE;
import static com.example.runningapp.activity.ActivityActivityNewEdit.EXTRA_ACTIVITY_TYPE_ID;
import static com.example.runningapp.activity.ActivityActivityNewEdit.EXTRA_ID;
import static com.example.runningapp.activity.ActivityActivityNewEdit.START_DATE;

public class StatisticFragment extends Fragment {
    private static final int ADD_NOTE_REQUEST = 1;
    private static final int EDIT_NOTE_REQUEST = 2;


    private ActivityCategoryViewModel activityCategoryViewModel;
    private ActivityTypeViewModel activityTypeViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_activity_main, container, false);

        FloatingActionButton buttonAddActivity = root.findViewById(R.id.button_add_activity);
        buttonAddActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActivityActivityNewEdit.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        final ActivityCategoryAdapter adapter = new ActivityCategoryAdapter();
        recyclerView.setAdapter(adapter);

        activityCategoryViewModel = ViewModelProviders.of(this).get(ActivityCategoryViewModel.class);
        activityCategoryViewModel.getAllActivityCategories().observe(this, new Observer<List<ActivityCategory>>() {

            @Override
            public void onChanged(List<ActivityCategory> activityCategories) {
                adapter.submitList(activityCategories);
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
                activityCategoryViewModel.delete(adapter.getActivityCategoryAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(), "Activity deleted", Toast.LENGTH_SHORT).show();
            }

        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ActivityCategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ActivityCategory activityCategory) {
                Intent intent = new Intent(getContext(), ActivityActivityNewEdit.class);
                intent.putExtra(EXTRA_ID, activityCategory.getId());
                intent.putExtra(EXTRA_ACTIVITY_TYPE_ID, String.valueOf(activityCategory.getActivity_type_id()));
                intent.putExtra(START_DATE, String.valueOf(activityCategory.getStart_date()));
                intent.putExtra(END_DATE, String.valueOf(activityCategory.getCategoryName()));
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }

        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String activityActivity = data.getStringExtra(EXTRA_ACTIVITY_TYPE_ID);
            String timeActivity = data.getStringExtra(START_DATE);
            String speedActivity = data.getStringExtra(END_DATE);

            Activity activity = new Activity(activityActivity, timeActivity, speedActivity);
            activityCategoryViewModel.insert(activity);

        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(EXTRA_ID, -1);

            if (id == -1) {
                return;
            }

            String activityType = data.getStringExtra(EXTRA_ACTIVITY_TYPE_ID);
            String timeActivity = data.getStringExtra(START_DATE);
            String speedActivity = data.getStringExtra(END_DATE);

            Activity activity = new Activity(activityType, timeActivity, speedActivity);
            activity.setId(id);
            activityCategoryViewModel.update(activity);

        } else {
        }
    }
}

