package com.example.sidkathuria14.healthcare.database;

import android.provider.BaseColumns;

/**
 * Created by theninetails on 27/1/18.
 */

public final class MedScheduleContract {
    private MedScheduleContract(){

    }

    public static class MedScheduleEntry implements BaseColumns{
        public static final String COLUMN_ID="_id";
        public static final String TABLE_NAME="medschedule";
        public static final String COLUMN_MED_NAME="medname";
        public static final String COLUMN_TIME_REM="reminders";
    }
}

