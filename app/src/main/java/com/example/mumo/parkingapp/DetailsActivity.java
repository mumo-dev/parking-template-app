package com.example.mumo.parkingapp;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mumo.parkingapp.adapters.SlotAdapter;
import com.example.mumo.parkingapp.model.BookedSlot;
import com.example.mumo.parkingapp.networking.ApiRestClient;
import com.example.mumo.parkingapp.utils.PreferenceUtils;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = "DetailsActivity";

    private RecyclerView mRecyclerView;
    private TextView mErrorTextView;
    private ProgressBar mProgressBar;
    private SlotAdapter mAdapter;
    private List<BookedSlot> mBookedSlots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mErrorTextView = findViewById(R.id.tv_errors);
        mProgressBar = findViewById(R.id.loading_indicator);

        mRecyclerView = findViewById(R.id.booked_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBookedSlots = new ArrayList<>();
        mAdapter = new SlotAdapter(this, mBookedSlots);
        mRecyclerView.setAdapter(mAdapter);
        fetchData();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchData() {
        int user_id = PreferenceUtils.getUserId(this);
        ApiRestClient.get("/api/parkings/user/" +user_id, null, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                mProgressBar.setVisibility(View.VISIBLE);
                mErrorTextView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                mProgressBar.setVisibility(View.INVISIBLE);
                mErrorTextView.setVisibility(View.INVISIBLE);
                Log.i(TAG, " data: " + response);
                List<BookedSlot> bookedSlots = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject obj = response.getJSONObject(i);
                        Log.i(TAG, "response loc" + obj.getString("location"));
                        JSONArray slotsArray = obj.getJSONArray("slots");

                        Log.i(TAG, " slots data length  : " + slotsArray.length());
                        for (int j = 0; j < slotsArray.length(); j++) {
                            JSONObject timeslots = slotsArray.getJSONObject(j);
                            int slot_id = timeslots.getInt("id");
                            String ref_no = timeslots.getString("ref_no");
                            String timestamp = timeslots.getString("updated_at");
                            String times = timeslots.getString("time_slots");

                            JSONObject object = new JSONObject(times);
                            Iterator<?> keys = object.keys();
                            while (keys.hasNext()){

                                String key = (String)keys.next();
                                if (object.getBoolean(key)){
                                    Log.i(TAG, "slot id: " + slot_id + " time slots: " + key);
                                    BookedSlot bookedSlot = new BookedSlot(slot_id,ref_no,key,timestamp);
                                    bookedSlots.add(bookedSlot);
                                }
                            }

                        }
                        mAdapter.setBookedSlots(bookedSlots);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                mProgressBar.setVisibility(View.INVISIBLE);
                mErrorTextView.setVisibility(View.VISIBLE);
                mErrorTextView.setText(throwable.getMessage());
                throwable.printStackTrace();
                Log.i(TAG, "onFailure: " + errorResponse);
            }
        });
    }
}
