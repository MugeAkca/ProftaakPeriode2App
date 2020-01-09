package com.example.runningapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.runningapp.activity.ActivityTypeNewEdit;
import com.example.runningapp.adapter.CategoryAdapter;
import com.example.runningapp.database.entity.ActivityType;
import com.example.runningapp.database.entity.Category;
import com.example.runningapp.viewmodel.CategoryViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.runningapp.activity.ActivityTypeNewEdit.EXTRA_ACTIVITY_TYPE_NAME;
import static com.example.runningapp.activity.ActivityTypeNewEdit.EXTRA_ID;

public class ActivityTypeFragment extends Fragment {

    private static final int ADD_NOTE_REQUEST = 1;
    private static final int EDIT_NOTE_REQUEST = 2;

    private CategoryViewModel categoryViewModel;
    private Category category;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_activity_type_main, container, false);


        FloatingActionButton buttonAddCategory = root.findViewById(R.id.button_add_activity_type);
        buttonAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActivityTypeNewEdit.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        final CategoryAdapter adapter = new  CategoryAdapter();
        recyclerView.setAdapter(adapter);

        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.getAllCategorys().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categorys) {
                adapter.submitList(categorys);
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
                categoryViewModel.delete(adapter.getCategoryAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(), "Category deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new  CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Category category) {
                Intent intent = new Intent(getContext(), ActivityType.class);
                intent.putExtra(EXTRA_ID, category.getId());
                intent.putExtra(EXTRA_ACTIVITY_TYPE_NAME, category.getName());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });

        return root;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String categoryName = data.getStringExtra(EXTRA_ACTIVITY_TYPE_NAME);

            Category category = new Category(categoryName);
            categoryViewModel.insert(category);

        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(EXTRA_ID, -1);

            if (id == -1){
                return;
            }

            String categoryName = data.getStringExtra(EXTRA_ACTIVITY_TYPE_NAME);

            Category category = new Category(categoryName);
            category.setId(id);
            categoryViewModel.update(category);


        } else {
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all_activity_types) {
            categoryViewModel.deleteAllCategorys(category);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}