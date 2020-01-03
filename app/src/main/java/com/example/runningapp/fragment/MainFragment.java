package com.example.runningapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.runningapp.R;
import com.example.runningapp.activity.ActivityStartActivity;

public class MainFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View root = inflater.inflate(R.layout.activity_home, container, false);

        Button btnStartActivity = root.findViewById(R.id.btnStartActivity);

        btnStartActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ActivityStartActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        return root;
    }
}