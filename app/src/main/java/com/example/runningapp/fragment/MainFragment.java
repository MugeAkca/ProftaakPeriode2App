package com.example.runningapp.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.runningapp.R;
import com.example.runningapp.activity.ActivitySelectActivityType;
import com.example.runningapp.activity.ClimateControllerActivity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.runningapp.activity.ActivityEndActivity.ACTIVITY_START_TIME;

public class MainFragment extends Fragment {

    private Date date;
    private long time;
    private View root;
    private Button btnStartActivity;
    private SimpleDateFormat simpleDateFormat;

    @SuppressLint("SimpleDateFormat")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        simpleDateFormat = new SimpleDateFormat("DD-MM-yyyy HH:mm");
        date = new Date();
        final String ts = simpleDateFormat.format(date);


        root = inflater.inflate(R.layout.fragment_home, container, false);

        btnStartActivity = root.findViewById(R.id.btnStartActivity);

        btnStartActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivitySelectActivityType.class);
                intent.putExtra(ACTIVITY_START_TIME, ts);
                v.getContext().startActivity(intent);
            }
        });

        ImageView btn = root.findViewById(R.id.weatherButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ClimateControllerActivity.class);
                v.getContext().startActivity(intent);

            }
        });


        return root;
    }
}
