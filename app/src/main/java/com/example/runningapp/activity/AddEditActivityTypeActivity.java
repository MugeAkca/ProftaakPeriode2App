package com.example.runningapp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.runningapp.R;


public class AddEditActivityTypeActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "EXTRA_ID";
    public static final String EXTRA_ACTIVITY_TYPE_NAME =
            "EXTRA_ACTIVITY_TYPE_NAME";


    private EditText editTextActivityType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_add_activity);

        editTextActivityType = findViewById(R.id.edit_activity_type_name);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Edit ActivityType");
            editTextActivityType.setText(intent.getStringExtra(EXTRA_ACTIVITY_TYPE_NAME));
        }else {
            setTitle("Add ActivityType");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void saveActivityType() {
        String activityTypeName = editTextActivityType.getText().toString();

        if (activityTypeName.trim().isEmpty()) {
            Toast.makeText(this, "Vul een activiteit, tijd of activityType in", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_ACTIVITY_TYPE_NAME, activityTypeName);

        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if (id != -1){
            data.putExtra(EXTRA_ID,id);
        }

        setResult(RESULT_OK, data);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_activity_type, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_activity_type) {
            saveActivityType();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


