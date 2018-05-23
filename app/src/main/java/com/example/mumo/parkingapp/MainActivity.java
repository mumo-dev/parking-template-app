package com.example.mumo.parkingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mumo.parkingapp.adapters.DataAdapter;
import com.example.mumo.parkingapp.data.BookingLot;
import com.example.mumo.parkingapp.data.FakeData;
import com.example.mumo.parkingapp.model.Parking;
import com.example.mumo.parkingapp.model.Slot;
import com.example.mumo.parkingapp.networking.ApiRestClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private DataAdapter mAdapter;
    private ActionBarDrawerToggle mToggle;
    private static final String TAG = "MainActivity";

    private List<Parking> mParkingList;

    private ProgressBar mProgressBar;
    private TextView mErrorTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mProgressBar = findViewById(R.id.pb_loading);
        mErrorTextView = findViewById(R.id.tv_errors_box);
        final DrawerLayout mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.nav_dashboard:
                        startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                        return true;
                    case R.id.nav_account:
                        startActivity(new Intent(MainActivity.this, AccountActivity.class));
                        return true;
                    case R.id.nav_settings:

                        return true;
                    case R.id.nav_slots:

                        return true;
                    default:
                        return true;

                }

            }
        });

        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//        List<BookingLot> fakeData = new FakeData().getBookingLots();
//        mAdapter = new DataAdapter(this, fakeData);
        mParkingList = new ArrayList<>();
        mAdapter = new DataAdapter(this, mParkingList);
        mRecyclerView.setAdapter(mAdapter);
        Log.i(TAG, "onCreate: ");
        fetchData();

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchData() {
        ApiRestClient.get("/api/parkings", null, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                mProgressBar.setVisibility(View.VISIBLE);
                mErrorTextView.setVisibility(View.INVISIBLE);
                mRecyclerView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
//                Log.i(TAG, "onSuccess: " + response.length());
                mProgressBar.setVisibility(View.INVISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mErrorTextView.setVisibility(View.INVISIBLE);

                List<Parking> parkings = new ArrayList<>();
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            String location = jsonObject.getString("location");
                            int fee = jsonObject.getInt("fee");
                            int startTime = jsonObject.getInt("start_time");
                            int endTime = jsonObject.getInt("end_time");
                            String date = jsonObject.getString("created_at");
                            JSONArray slotsArray = jsonObject.getJSONArray("slots");
                            List<Slot> parkingSlots = new ArrayList<>();
                            for (int j = 0; j < slotsArray.length(); j++) {
                                JSONObject slot = slotsArray.getJSONObject(j);
                                int slotId = slot.getInt("id");
                                String slotRefNo = slot.getString("ref_no");
                                Slot newSlot = new Slot(slotId, slotRefNo);
                                parkingSlots.add(newSlot);

                            }
                            Parking parking = new Parking(id, location, fee, parkingSlots, startTime, endTime, date);
//                            Log.i(TAG, "Parking time slots: "+parking.getTimeSlots().toString());
                            parkings.add(parking);
//                            Log.i(TAG, "slots array:" + slotsArray.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mAdapter.setListItems(parkings);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
//                Log.i(TAG, "onFailure: " + errorResponse.toString());
                mProgressBar.setVisibility(View.INVISIBLE);
                mRecyclerView.setVisibility(View.INVISIBLE);
                mErrorTextView.setVisibility(View.VISIBLE);
                mErrorTextView.setText(throwable.getMessage());
            }
        });
    }
}
