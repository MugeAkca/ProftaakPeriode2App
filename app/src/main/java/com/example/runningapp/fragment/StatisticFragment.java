package com.example.runningapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.print.PrinterId;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.runningapp.R;
import com.example.runningapp.activity.ActivityActivityNewEdit;
import com.example.runningapp.adapter.ActivityAdapter;
import com.example.runningapp.database.entity.ActivityActivitySubType;
import com.example.runningapp.viewmodel.ActivityViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.runningapp.activity.ActivityActivityNewEdit.ACTIVITY_NEW_EDIT_END_DATE;
import static com.example.runningapp.activity.ActivityActivityNewEdit.ACTIVITY_NEW_EDIT_TYPE_ID;
import static com.example.runningapp.activity.ActivityActivityNewEdit.ACTIVITY_NEW_EDIT_TYPE_NAME;
import static com.example.runningapp.activity.ActivityActivityNewEdit.ACTIVITY_NEW_EDIT_ID;
import static com.example.runningapp.activity.ActivityActivityNewEdit.ACTIVITY_NEW_EDIT_START_DATE;

public class StatisticFragment extends Fragment {
    private static final int ADD_ACTIVITY_REQUEST = 1;
    private static final int EDIT_ACTIVITY_REQUEST = 2;

    private ActivityViewModel activityViewModel;
    private View root;
//    private FloatingActionButton buttonAddActivity;
    private RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_activity_main, container, false);

//        buttonAddActivity = root.findViewById(R.id.button_add_activity);
//        buttonAddActivity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), ActivityActivityNewEdit.class);
//                startActivityForResult(intent, ADD_ACTIVITY_REQUEST);
//            }
//        });

        recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        final ActivityAdapter adapter = new ActivityAdapter();
        recyclerView.setAdapter(adapter);

        activityViewModel = ViewModelProviders.of(this).get(ActivityViewModel.class);
        activityViewModel.getAllActivities().observe(this, new Observer<List<ActivityActivitySubType>>() {
            @Override
            public void onChanged(List<ActivityActivitySubType> activitysSubType) {
                adapter.submitList(activitysSubType);
            }
        });


//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
//                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                activityViewModel.delete(adapter.getActivitySubTypeAt(viewHolder.getAdapterPosition()));
//                Toast.makeText(getContext(), "Activity deleted", Toast.LENGTH_SHORT).show();
//            }
//
//        }).attachToRecyclerView(recyclerView);


        adapter.setOnItemClickListener(new ActivityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ActivityActivitySubType activityActivitySubType) {
                Intent intent = new Intent(getContext(), ActivityActivityNewEdit.class);
                intent.putExtra(ACTIVITY_NEW_EDIT_ID, String.valueOf(activityActivitySubType.getId()));
                intent.putExtra(ACTIVITY_NEW_EDIT_TYPE_ID, String.valueOf(activityActivitySubType.getActivity_type_id()));
                intent.putExtra(ACTIVITY_NEW_EDIT_TYPE_NAME, String.valueOf(activityActivitySubType.getName()));
                intent.putExtra(ACTIVITY_NEW_EDIT_START_DATE, String.valueOf(activityActivitySubType.getStart_date()));
                intent.putExtra(ACTIVITY_NEW_EDIT_END_DATE, String.valueOf(activityActivitySubType.getEnd_date()));
                startActivityForResult(intent, EDIT_ACTIVITY_REQUEST);
            }
        });

        return root;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == ADD_GOAL_REQUEST && resultCode == RESULT_OK) {
//            int activityActivity = Integer.valueOf(data.getStringExtra(ACTIVITY_NEW_EDIT_TYPE_ID));
//            String timeActivity = data.getStringExtra(ACTIVITY_NEW_EDIT_START_DATE);
//            String speedActivity = data.getStringExtra(ACTIVITY_NEW_EDIT_END_DATE);
//
//
//            Activity activity = new ActivityActivitySubType(activityActivity, timeActivity, speedActivity, timeActivity);
//            activityViewModel.insert(activity);
//
//        } else if (requestCode == SELECT_ACTIVITY_TYPE_REQUEST && resultCode == RESULT_OK) {
//            int id = data.getIntExtra(ACTIVITY_NEW_EDIT_ID, -1);
//
//            if (id == -1) {
//                return;
//            }
//
//            String activityType = data.getStringExtra(ACTIVITY_NEW_EDIT_TYPE_ID);
//            String timeActivity = data.getStringExtra(ACTIVITY_NEW_EDIT_START_DATE);
//            String speedActivity = data.getStringExtra(ACTIVITY_NEW_EDIT_END_DATE);
//
//            Activity activity = new Activity(activityType, timeActivity, speedActivity);
//            activity.setId(id);
//            activityViewModel.update(activity);
//
//        } else {
//        }
//    }
}

