package com.example.sidkathuria14.healthcare.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.sidkathuria14.healthcare.R;
import com.example.sidkathuria14.healthcare.adapter.MedAdapter;
import com.example.sidkathuria14.healthcare.database.MedScheduleContract;
import com.example.sidkathuria14.healthcare.database.MedScheduleDBHelper;

import java.util.ArrayList;

public class MedSchedulesViewActivity extends AppCompatActivity {
    LinearLayout llemptyView;
    RecyclerView rvMedNames;
    FloatingActionButton fabMed;
    MedAdapter medAdapter;
    ArrayList<String> medList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_schedules_view);

        llemptyView=findViewById(R.id.emptyView);
        rvMedNames=findViewById(R.id.rvMedNames);
        fabMed=findViewById(R.id.fabMed);

        medList=getMedList();

        medAdapter=new MedAdapter(this,medList);

        rvMedNames.setLayoutManager( new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvMedNames.setAdapter(medAdapter);

        fabMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedSchedulesViewActivity.this,MedSchedulerActivity.class);
                intent.putExtra("opMode","new");
                startActivity(intent);
            }
        });

    }

    private boolean checkDB(){
        MedScheduleDBHelper helper = new MedScheduleDBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] projection = new String[]{MedScheduleContract.MedScheduleEntry.COLUMN_MED_NAME};
        Cursor cursor = db.query(
                MedScheduleContract.MedScheduleEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null

        );
        return !cursor.moveToNext();
    }

    public ArrayList<String> getMedList(){
        MedScheduleDBHelper helper = new MedScheduleDBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        String[]projections = new String[]{
                MedScheduleContract.MedScheduleEntry.COLUMN_MED_NAME
        };

        ArrayList<String> list = new ArrayList<>();

        Cursor cursor = db.query(
                MedScheduleContract.MedScheduleEntry.TABLE_NAME,
                projections,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()){
            list.add(cursor.getString(cursor.getColumnIndexOrThrow(MedScheduleContract.MedScheduleEntry.COLUMN_MED_NAME)));
        }
        return list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(checkDB()){
           llemptyView.setVisibility(View.VISIBLE);
           rvMedNames.setVisibility(View.GONE);
        }else{
            llemptyView.setVisibility(View.GONE);
            rvMedNames.setVisibility(View.VISIBLE);
            medAdapter.updateList(getMedList());
        }
    }
}
