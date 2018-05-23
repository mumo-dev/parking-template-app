package com.example.mumo.parkingapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.mumo.parkingapp.adapters.SlotAdapter;
import com.example.mumo.parkingapp.database.ParkingDbContract;
import com.example.mumo.parkingapp.database.ParkingDbContract.SlotsSchema;
import com.example.mumo.parkingapp.database.ParkingDbHelper;
import com.example.mumo.parkingapp.model.BookedSlot;

import java.util.ArrayList;
import java.util.List;

public class SlotsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SlotAdapter mAdapter;
    private List<BookedSlot> mSlotList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_slots);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mRecyclerView = findViewById(R.id.slots_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        mSlotList = new ArrayList<>();
        new  FetchSlotsAsyncTask().execute();
        mAdapter = new SlotAdapter(this, mSlotList);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    private  void fetchItems() {
        SQLiteDatabase db = new ParkingDbHelper(this).getReadableDatabase();
        Cursor cursor = db.query(SlotsSchema.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                SlotsSchema._ID
        );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String refno = cursor.getString(cursor.getColumnIndex(SlotsSchema.COL_REF_NO));
            String timebooked = cursor.getString(cursor.getColumnIndex(SlotsSchema.COL_TIME_BOOKED));
            String timeStamp = cursor.getString(cursor.getColumnIndex(SlotsSchema.COL_TIMESTAMP));
            int slotId= cursor.getInt(cursor.getColumnIndex(SlotsSchema.COL_SLOT_ID));
            int id= cursor.getInt(cursor.getColumnIndex(SlotsSchema._ID));
            BookedSlot slot = new BookedSlot(id,slotId, refno, timebooked,timeStamp);
            mSlotList.add(slot);
            cursor.moveToNext();
        }
        mAdapter.setBookedSlots(mSlotList);
    }

    private  class FetchSlotsAsyncTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            fetchItems();
            return null;
        }
    }
}
