package com.example.mumo.parkingapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mumo.parkingapp.database.ParkingDbContract.SlotsSchema;

public class ParkingDbHelper extends SQLiteOpenHelper {


    private static final String DATABASE = "parking.db";
    private static final int VERSION = 1;

    public ParkingDbHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE " + SlotsSchema.TABLE_NAME + "(" +
                SlotsSchema._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                SlotsSchema.COL_REF_NO + " TEXT NOT NULL , " +
                SlotsSchema.COL_SLOT_ID + " INTEGER NOT NULL , " +
                SlotsSchema.COL_TIME_BOOKED + " TEXT NOT NULL , " +
                SlotsSchema.COL_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP " +
                ");";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL("DROP TABLE IF EXISTS "+ SlotsSchema.TABLE_NAME);
       onCreate(db);
    }
}
