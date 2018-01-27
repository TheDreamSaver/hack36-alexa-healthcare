package com.example.sidkathuria14.healthcare.activities;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import com.example.sidkathuria14.healthcare.MedReminderIntentService;
import com.example.sidkathuria14.healthcare.MedTextView;
import com.example.sidkathuria14.healthcare.R;
import com.example.sidkathuria14.healthcare.adapter.TimeScheduleAdapter;
import com.example.sidkathuria14.healthcare.database.MedScheduleContract;
import com.example.sidkathuria14.healthcare.database.MedScheduleDBHelper;
import com.example.sidkathuria14.healthcare.interfaces.LongClickListener;

import java.util.ArrayList;
import java.util.Calendar;

public class MedSchedulerActivity extends AppCompatActivity  implements android.app.TimePickerDialog.OnTimeSetListener {
    MedTextView medName;
    AlarmManager alarmManager;
    RecyclerView rvTimeSchedules;
    TimeScheduleAdapter scheduleAdapter;
    RelativeLayout timeEmptyView;
    ArrayList<String> timerList;
    FloatingActionButton fab;
    String opMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_scheduler);
        alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);

        medName=findViewById(R.id.medName);
        medName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medName.showDialog();
            }
        });

        opMode=getIntent().getStringExtra("opMode");
        if(opMode.equals("update")) {
            medName.setText(getIntent().getStringExtra("MedName"));
            timerList = getTimeListFromDB(getIntent().getStringExtra("MedName"));
            Log.d("MedScheduler", "onCreate: "+timerList);
        }else{
            timerList=new ArrayList<>();
        }
        timeEmptyView=findViewById(R.id.emptyView);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                android.app.TimePickerDialog timePickerDialog =
                        new android.app.TimePickerDialog(
                                MedSchedulerActivity.this,
                                MedSchedulerActivity.this,
                                now.get(Calendar.HOUR_OF_DAY),
                                now.get(Calendar.MINUTE),
                                true
                        );
                timePickerDialog.show();
            }
        });



        rvTimeSchedules=findViewById(R.id.rvTimeSchedules);
        rvTimeSchedules.setLayoutManager(new LinearLayoutManager(
                this,LinearLayoutManager.VERTICAL,false
        ));

        scheduleAdapter = new TimeScheduleAdapter(this,timerList);
        scheduleAdapter.setLongClickListener(new LongClickListener() {
            @Override
            public void onLongClick(String name) {
                deleteTime(name);
            }
        });
        rvTimeSchedules.setAdapter(scheduleAdapter);
    }

    @Override
    protected void onResume() {
        if(timerList.size()==0){
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_med_schedule,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.saveBtn:


                String timerString = createTimerString(timerList);
                Log.d("MedScheduler", "onOptionsItemSelected: "+timerString);
                Log.d("MedScheduler", "onOptionsItemSelected: "+timerList);
                ContentValues values = new ContentValues();
                values.put(MedScheduleContract.MedScheduleEntry.COLUMN_MED_NAME,medName.getMedName());
                values.put(MedScheduleContract.MedScheduleEntry.COLUMN_TIME_REM,timerString);

                MedScheduleDBHelper helper = new MedScheduleDBHelper(this);
                SQLiteDatabase db = helper.getWritableDatabase();
                if(opMode.equals("new")) {
                    db.insert(
                            MedScheduleContract.MedScheduleEntry.TABLE_NAME, null, values
                    );
                }else{
                    Log.d("MedScheduler","enter update");
                    db.update(
                            MedScheduleContract.MedScheduleEntry.TABLE_NAME,
                            values,
                            MedScheduleContract.MedScheduleEntry.COLUMN_MED_NAME+" LIKE ?",
                            new String[]{medName.getMedName()}
                    );
                }

                for(String thisTime :timerList){
                    int hour = Integer.parseInt(thisTime.substring(0,2));
                    int min =Integer.parseInt(thisTime.substring(3,5));
                    setAlarmForMed(hour,min);
                }

                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        timerList.add(String.format("%02d:%02d",hourOfDay,minute));
        scheduleAdapter.updateList(timerList);

    }

    private void setAlarmForMed(int hour, int minute){
        Intent intent = new Intent(this, MedReminderIntentService.class);
        intent.putExtra("MedName",medName.getMedName());
        PendingIntent alarmIntent = PendingIntent.getService(
                this,
                hour+minute,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());
        now.set(Calendar.HOUR_OF_DAY,hour);
        now.set(Calendar.MINUTE,minute);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,now.getTimeInMillis(),AlarmManager.INTERVAL_DAY,alarmIntent);
    }

    private void deleteTime(final String thisTime){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setMessage("Delete this ?")
                .setPositiveButton(R.string.btn_OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int i =0;i<timerList.size();i++){
                            if(timerList.get(i).equals(thisTime)){
                                timerList.remove(i);
                                break;
                            }
                        }
                        scheduleAdapter.updateList(timerList);
                    }
                })
                .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();

    }
}
