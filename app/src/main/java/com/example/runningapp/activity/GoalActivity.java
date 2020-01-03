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

import com.example.runningapp.adapter.GoalAdapter;
import com.example.runningapp.viewmodel.GoalViewModel;
import com.example.runningapp.R;
import com.example.runningapp.database.entity.Goal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class GoalActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    private GoalViewModel goalViewModel;
    private Goal goal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal_main);

        FloatingActionButton buttonAddGoal = findViewById(R.id.button_add_goal);
        buttonAddGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GoalActivity.this, AddEditGoalActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final GoalAdapter adapter = new  GoalAdapter();
        recyclerView.setAdapter(adapter);

        goalViewModel = ViewModelProviders.of(this).get(GoalViewModel.class);
        goalViewModel.getAllGoals().observe(this, new Observer<List<Goal>>() {
            @Override
            public void onChanged(List<Goal> goals) {
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
                Toast.makeText(GoalActivity.this, "Goal deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new  GoalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Goal goal) {
                Intent intent = new Intent(GoalActivity.this, AddEditGoalActivity.class);
                intent.putExtra(AddEditGoalActivity.EXTRA_ID, goal.getId());
                intent.putExtra(AddEditGoalActivity.EXTRA_ACTIVITY_TYPE_ID, String.valueOf(goal.getActivity_type_id()));
                intent.putExtra(AddEditGoalActivity.EXTRA_TIME_GOAL, String.valueOf(goal.getTime_goal()));
                intent.putExtra(AddEditGoalActivity.EXTRA_SPEED_GOAL, String.valueOf(goal.getSpeed_goal()));
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String goalActivity = data.getStringExtra(AddEditGoalActivity.EXTRA_ACTIVITY_TYPE_ID);
            String timeGoal = data.getStringExtra(AddEditGoalActivity.EXTRA_TIME_GOAL);
            String speedGoal = data.getStringExtra(AddEditGoalActivity.EXTRA_SPEED_GOAL);

            Goal goal = new Goal(goalActivity, timeGoal, speedGoal);
            goalViewModel.insert(goal);

            Toast.makeText(this, "Goal saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditGoalActivity.EXTRA_ID, -1);

            if (id == -1){
                Toast.makeText(this, "Goal can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String activityType = data.getStringExtra(AddEditGoalActivity.EXTRA_ACTIVITY_TYPE_ID);
            String timeGoal = data.getStringExtra(AddEditGoalActivity.EXTRA_TIME_GOAL);
            String speedGoal = data.getStringExtra(AddEditGoalActivity.EXTRA_SPEED_GOAL);

            Goal goal = new Goal(activityType, timeGoal, speedGoal);
            goal.setId(id);
            goalViewModel.update(goal);

            Toast.makeText(this, "Goal Updated", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Goal not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all_goals) {
            goalViewModel.deleteAllGoals(goal);
            Toast.makeText(this, "All goals deleted", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
