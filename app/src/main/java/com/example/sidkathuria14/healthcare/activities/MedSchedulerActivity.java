package com.example.sidkathuria14.healthcare.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;
import com.example.sidkathuria14.healthcare.MedTextView;
import com.example.sidkathuria14.healthcare.R;
import com.example.sidkathuria14.healthcare.adapter.TimeScheduleAdapter;
import com.example.sidkathuria14.healthcare.database.MedScheduleContract;
import com.example.sidkathuria14.healthcare.database.MedScheduleDBHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class MedSchedulerActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    MedTextView medName;
    RecyclerView rvTimeSchedules;
    TimeScheduleAdapter scheduleAdapter;
    RelativeLayout timeEmptyView;
    ArrayList<String> timerList;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_scheduler);
        timerList=getTimeListFromDB(getIntent().getStringExtra("MedName"));
        timeEmptyView=findViewById(R.id.emptyView);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog pickerDialog = TimePickerDialog.newInstance(
                        MedSchedulerActivity.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        true
                );
                pickerDialog.show(getFragmentManager(),"timepicker");
            }
        });

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

        scheduleAdapter = new TimeScheduleAdapter(this,new ArrayList<String>());
        rvTimeSchedules.setAdapter(scheduleAdapter);
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

    @Override
    protected void onResume() {
        if(!checkDB()){
            timeEmptyView.setVisibility(View.VISIBLE);
            rvTimeSchedules.setVisibility(View.GONE);
        }else{
            timeEmptyView.setVisibility(View.GONE);
            rvTimeSchedules.setVisibility(View.VISIBLE);
            scheduleAdapter.updateList(timerList);
        }
        super.onResume();
    }

    private ArrayList<String> getTimerList(String timeString){
        ArrayList<String> timeList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for( int i = 0;i<timeString.length();i++){
            if(timeString.charAt(i)==';'){
                timeList.add(sb.toString());
                sb.setLength(0);
            }else {
                sb.append(timeString.charAt(i));
            }

        }
        return timeList;
    }

    private String createTimerString(ArrayList<String> timerList){
        StringBuilder sb = new StringBuilder();
        for( String item :timerList){
            sb.append(item+";");
        }
        return  sb.toString();
    }

    private ArrayList<String>getTimeListFromDB(String medName){
        MedScheduleDBHelper helper = new MedScheduleDBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] projections = new String[]{
                MedScheduleContract.MedScheduleEntry.COLUMN_MED_NAME,
                MedScheduleContract.MedScheduleEntry.COLUMN_TIME_REM
        };
        String selection = MedScheduleContract.MedScheduleEntry.COLUMN_MED_NAME + " = ?";
        String [] selectionArgs = new String[]{medName};
        Cursor cursor = db.query(
                MedScheduleContract.MedScheduleEntry.TABLE_NAME,
                projections,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        String timerString = "";
        if(cursor.moveToNext()){
            timerString = cursor.getString(cursor.getColumnIndexOrThrow(MedScheduleContract.MedScheduleEntry.COLUMN_TIME_REM));
        }
        return getTimerList(timerString);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {

    }
}
