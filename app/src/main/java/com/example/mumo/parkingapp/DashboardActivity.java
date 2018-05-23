package com.example.mumo.parkingapp;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mumo.parkingapp.fragments.TimePickerFragment;


public class DashboardActivity extends AppCompatActivity implements TimePickerFragment.OnDataPass {

    private static final String START_TIME_CODE = "start", CLOSE_TIME_CODE = "close";
    private EditText mLocationEditText, mSlotsEditText, mFeeEditText;
    private TextView mStartTextViewPicker, mCloseTextViewPicker, mStartTextView, mCloseTextView, mErrorTextview;

    private int startTime;
    private int stopTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Dashboard");
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mErrorTextview = findViewById(R.id.tv_errors);
        mLocationEditText = findViewById(R.id.parking_location);
        mSlotsEditText = findViewById(R.id.parking_slots);
        mFeeEditText = findViewById(R.id.parking_fees);
        mStartTextViewPicker = findViewById(R.id.parking_start_time);
        mStartTextViewPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(START_TIME_CODE);
                dialog.show(manager, "TAG_TIME");
            }
        });
        mStartTextView = findViewById(R.id.parking_start_time_view);
        mCloseTextViewPicker = findViewById(R.id.parking_close_time);
        mCloseTextViewPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(CLOSE_TIME_CODE);
                dialog.show(manager, "TAG_TIME");
            }
        });
        mCloseTextView = findViewById(R.id.parking_close_time_view);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getTimePicked(int hour, int minute, String code) {
        if (code.equals(START_TIME_CODE)) {
            startTime = hour;
            mStartTextView.setText(formatTime(hour, minute) + " hrs");
        } else if (code.equals(CLOSE_TIME_CODE)) {
            //TODO validate input to ensure time picked is greater than start time
            stopTime = hour;
            mCloseTextView.setText(formatTime(hour, minute) + " hrs");
        }
    }

    private String formatTime(int hour, int minute) {
        if (hour < 10)
            return "0" + hour + ":" + "00";
        return hour + ":" + "00";

    }

    public void CreateParkingSpace(View view) {
        String location = mLocationEditText.getText().toString();

        int start = startTime;
        int stop = stopTime;
        if (location.length() > 0 && mSlotsEditText.getText().toString().length() > 0
                && mFeeEditText.getText().toString().length() > 0
                && start > 0 && stop > 0) {
            int slots = Integer.valueOf(mSlotsEditText.getText().toString());
            int fee = Integer.valueOf(mFeeEditText.getText().toString());
            //TODO submit request to server
            Toast.makeText(this,"Some thi",Toast.LENGTH_SHORT).show();
        } else {
            mErrorTextview.setText(R.string.error_message_null);
        }

    }
}
