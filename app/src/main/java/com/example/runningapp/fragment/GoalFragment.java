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
import com.example.runningapp.activity.AddEditGoalActivity;
import com.example.runningapp.adapter.GoalAdapter;
import com.example.runningapp.database.entity.Goal;
import com.example.runningapp.viewmodel.GoalViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class GoalFragment extends Fragment {
    private static final int ADD_NOTE_REQUEST = 1;
    private static final int EDIT_NOTE_REQUEST = 2;

    private GoalViewModel goalViewModel;
    private Goal goal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.goal_main, container, false);

        FloatingActionButton buttonAddGoal = root.findViewById(R.id.button_add_goal);
        buttonAddGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddEditGoalActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // recyclerview size wont change,
        recyclerView.setHasFixedSize(true);

        final GoalAdapter adapter = new GoalAdapter();
        // standard it is empty
        recyclerView.setAdapter(adapter);

        // Android descides if we need to initiate a new viewmodel (only if it doesn't excist)
        // lifecycle, destoryes when this lifecycle is destoryed
        goalViewModel = ViewModelProviders.of(this).get(GoalViewModel.class);
        goalViewModel.getAllGoals().observe(this, new Observer<List<Goal>>() {
            @Override
            public void onChanged(List<Goal> goals) {
                // update recyclerview
                adapter.submitList(goals);
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
                goalViewModel.delete(adapter.getGoalAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(), "Goal deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new GoalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Goal goal) {
                Intent intent = new Intent(getActivity(), AddEditGoalActivity.class);
                intent.putExtra(AddEditGoalActivity.EXTRA_ID, goal.getId());
                intent.putExtra(AddEditGoalActivity.EXTRA_ACTIVITY_TYPE_ID, String.valueOf(goal.getActivity_type_id()));
                intent.putExtra(AddEditGoalActivity.EXTRA_TIME_GOAL, String.valueOf(goal.getTime_goal()));
                intent.putExtra(AddEditGoalActivity.EXTRA_SPEED_GOAL, String.valueOf(goal.getSpeed_goal()));
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });

        return root;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String goalActivity = data.getStringExtra(AddEditGoalActivity.EXTRA_ACTIVITY_TYPE_ID);
            String timeGoal = data.getStringExtra(AddEditGoalActivity.EXTRA_TIME_GOAL);
            String speedGoal = data.getStringExtra(AddEditGoalActivity.EXTRA_SPEED_GOAL);

            Goal goal = new Goal(goalActivity, timeGoal, speedGoal);
            goalViewModel.insert(goal);

        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditGoalActivity.EXTRA_ID, -1);

            if (id == -1){
                return;
            }

            String activityType = data.getStringExtra(AddEditGoalActivity.EXTRA_ACTIVITY_TYPE_ID);
            String timeGoal = data.getStringExtra(AddEditGoalActivity.EXTRA_TIME_GOAL);
            String speedGoal = data.getStringExtra(AddEditGoalActivity.EXTRA_SPEED_GOAL);

            Goal goal = new Goal(activityType, timeGoal, speedGoal);
            goal.setId(id);
            goalViewModel.update(goal);


        } else {
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all_goals) {
            goalViewModel.deleteAllGoals(goal);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}



