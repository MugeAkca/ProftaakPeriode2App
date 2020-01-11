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
import com.example.runningapp.activity.ActivityGoalNewEdit;
import com.example.runningapp.activity.ActivitySelectActivityType;
import com.example.runningapp.activity.ActivitySelectActivityTypeGoal;
import com.example.runningapp.adapter.GoalAdapter;
import com.example.runningapp.database.entity.Goal;
import com.example.runningapp.database.entity.GoalActivitySubType;
import com.example.runningapp.viewmodel.GoalViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.runningapp.activity.ActivityGoalNewEdit.GOAL_NEW_EDIT_ACTIVITY_TYPE_ID;
import static com.example.runningapp.activity.ActivityGoalNewEdit.GOAL_NEW_EDIT_ACTIVITY_TYPE_NAME;
import static com.example.runningapp.activity.ActivityGoalNewEdit.GOAL_NEW_EDIT_ID;
import static com.example.runningapp.activity.ActivityGoalNewEdit.GOAL_NEW_EDIT_SPEED_GOAL;
import static com.example.runningapp.activity.ActivityGoalNewEdit.GOAL_NEW_EDIT_TIME_GOAL;

public class GoalFragment extends Fragment {
    public static final int ADD_GOAL_REQUEST = 1;
    public static final int EDIT_GOAL_REQUEST = 2;

    private GoalViewModel goalViewModel;
    private View root;
    private FloatingActionButton buttonAddGoal;
    private RecyclerView recyclerView;
    private String activityTypeId;
    private String timeGoal;
    private String speedGoal;
    private Goal goal;
    private Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_goal_main, container, false);

        buttonAddGoal = root.findViewById(R.id.button_add_goal);
        buttonAddGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getContext(), ActivitySelectActivityTypeGoal.class);
                getContext().startActivity(intent);
            }
        });

        recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // recyclerview size wont change,
        recyclerView.setHasFixedSize(true);

        final GoalAdapter adapter = new GoalAdapter();
        // standard it is empty
        recyclerView.setAdapter(adapter);

        // Android descides if we need to initiate a new viewmodel (only if it doesn't excist)
        // lifecycle, destoryes when this lifecycle is destoryed
        goalViewModel = ViewModelProviders.of(this).get(GoalViewModel.class);
        goalViewModel.getAllGoals().observe(this, new Observer<List<GoalActivitySubType>>() {

            @Override
            public void onChanged(List<GoalActivitySubType> goalActivitySubTypes) {
                adapter.submitList(goalActivitySubTypes);
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
                goalViewModel.delete(adapter.getGoalActivitySubTypeAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(), "Goal deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new GoalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GoalActivitySubType goalActivitySubType) {
                Intent intent = new Intent(getActivity(), ActivityGoalNewEdit.class);
                intent.putExtra(GOAL_NEW_EDIT_ID, goalActivitySubType.getId());
                intent.putExtra(GOAL_NEW_EDIT_ACTIVITY_TYPE_ID, String.valueOf(goalActivitySubType.getActivity_type_id()));
                intent.putExtra(GOAL_NEW_EDIT_ACTIVITY_TYPE_NAME, String.valueOf(goalActivitySubType.getName()));
                intent.putExtra(GOAL_NEW_EDIT_TIME_GOAL, String.valueOf(goalActivitySubType.getTime_goal()));
                intent.putExtra(GOAL_NEW_EDIT_SPEED_GOAL, String.valueOf(goalActivitySubType.getSpeed_goal()));
                startActivityForResult(intent, EDIT_GOAL_REQUEST);
            }
        });

        return root;

    }
//
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == ADD_GOAL_REQUEST && resultCode == RESULT_OK) {
//            activityTypeId = data.getStringExtra(GOAL_NEW_EDIT_ACTIVITY_TYPE_ID);
//            timeGoal = data.getStringExtra(GOAL_NEW_EDIT_TIME_GOAL);
//            speedGoal = data.getStringExtra(GOAL_NEW_EDIT_SPEED_GOAL);
//
//            goal = new Goal(activityTypeId, timeGoal, speedGoal);
//            goalViewModel.insert(goal);
//
//        } else if (requestCode == EDIT_GOAL_REQUEST && resultCode == RESULT_OK) {
//            int id = data.getIntExtra(GOAL_NEW_EDIT_ID, -1);
//
//            if (id == -1) {
//                return;
//            }
//
//            activityTypeId = data.getStringExtra(GOAL_NEW_EDIT_ACTIVITY_TYPE_ID);
//            timeGoal = data.getStringExtra(GOAL_NEW_EDIT_TIME_GOAL);
//            speedGoal = data.getStringExtra(GOAL_NEW_EDIT_SPEED_GOAL);
//
//            goal = new Goal(activityTypeId, timeGoal, speedGoal);
//            goal.setId(id);
//            goalViewModel.update(goal);
//
//        }
//    }


}



