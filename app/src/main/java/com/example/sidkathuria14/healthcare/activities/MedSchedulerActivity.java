package com.example.sidkathuria14.healthcare.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.sidkathuria14.healthcare.MedTextView;
import com.example.sidkathuria14.healthcare.R;

public class MedSchedulerActivity extends AppCompatActivity {
    MedTextView medName;
    RecyclerView rvTimeSchedules;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_scheduler);

        medName=findViewById(R.id.medName);
        medName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medName.showDialog();
            }
        });

        rvTimeSchedules=findViewById(R.id.rvTimeSchedules);
        rvTimeSchedules.setLayoutManager(new LinearLayoutManager(
                this,LinearLayoutManager.VERTICAL,false
        ));
    }
}
