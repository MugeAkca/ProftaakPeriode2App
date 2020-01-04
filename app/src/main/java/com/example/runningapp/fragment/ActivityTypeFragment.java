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
import com.example.runningapp.activity.AddEditActivityTypeActivity;
import com.example.runningapp.adapter.ActivityTypeAdapter;
import com.example.runningapp.database.entity.ActivityType;
import com.example.runningapp.viewmodel.ActivityTypeViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ActivityTypeFragment extends Fragment {

    private static final int ADD_NOTE_REQUEST = 1;
    private static final int EDIT_NOTE_REQUEST = 2;

    private ActivityTypeViewModel activityTypeViewModel;
    private ActivityType activityType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_type_main, container, false);


        FloatingActionButton buttonAddActivityType = root.findViewById(R.id.button_add_activity_type);
        buttonAddActivityType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddEditActivityTypeActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                activityTypeViewModel.delete(adapter.getActivityTypeAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(), "ActivityType deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new  ActivityTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ActivityType activityType) {
                Intent intent = new Intent(getContext(), AddEditActivityTypeActivity.class);
                intent.putExtra(AddEditActivityTypeActivity.EXTRA_ID, activityType.getType_id());
                intent.putExtra(AddEditActivityTypeActivity.EXTRA_ACTIVITY_TYPE_NAME, activityType.getName());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });

        return root;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String activityTypeName = data.getStringExtra(AddEditActivityTypeActivity.EXTRA_ACTIVITY_TYPE_NAME);

            ActivityType activityType = new ActivityType(activityTypeName);
            activityTypeViewModel.insert(activityType);

        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditActivityTypeActivity.EXTRA_ID, -1);

            if (id == -1){
                return;
            }

            String activityTypeName = data.getStringExtra(AddEditActivityTypeActivity.EXTRA_ACTIVITY_TYPE_NAME);

            ActivityType activityType = new ActivityType(activityTypeName);
            activityType.setType_id(id);
            activityTypeViewModel.update(activityType);


        } else {
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all_activity_types) {
            activityTypeViewModel.deleteAllActivityTypes(activityType);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

