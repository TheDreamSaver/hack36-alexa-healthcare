package com.example.sidkathuria14.healthcare.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by theninetails on 27/1/18.
 */

public class MedScheduleDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MedSChedule.db";
    public static final int DATABASE_VER = 1;

    private static final String CREATE_ENTRIES = "CREATE TABLE " + MedScheduleContract.MedScheduleEntry.TABLE_NAME +
            " (" + MedScheduleContract.MedScheduleEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
            MedScheduleContract.MedScheduleEntry.COLUMN_MED_NAME + " TEXT, " +
            MedScheduleContract.MedScheduleEntry.COLUMN_TIME_REM + " TEXT)";

    private static final String DEL_ENTRIES = "DROP TABLE IF EXISTS " + MedScheduleContract.MedScheduleEntry.TABLE_NAME;

    public MedScheduleDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DEL_ENTRIES);
        onCreate(db);
    }
}
