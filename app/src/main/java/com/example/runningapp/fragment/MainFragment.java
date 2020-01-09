package com.example.runningapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import com.example.runningapp.R;
import com.example.runningapp.activity.ActivitySelectActivityType;

import java.sql.Timestamp;
import java.util.Date;
import static com.example.runningapp.activity.ActivityEndActivity.START_TIME;

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        Date date = new Date();
        long time = date.getTime();
        final Timestamp ts = new Timestamp(time);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Button btnStartActivity = root.findViewById(R.id.btnStartActivity);

        btnStartActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivitySelectActivityType.class);
                intent.putExtra(START_TIME, ts.toString());
                v.getContext().startActivity(intent);
            }
        });

        return root;
    }
}
