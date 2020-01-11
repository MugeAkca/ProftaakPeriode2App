package com.example.runningapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.runningapp.R;

public class ActivityEndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_end);

        Button btnCancelActivity = findViewById(R.id.btnCancelActivity);

        btnCancelActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityStartActivity.class);
                v.getContext().startActivity(intent);

            }
        });
    }
}
