package com.example.mumo.parkingapp;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mumo.parkingapp.fragments.TimePickerFragment;
import com.example.mumo.parkingapp.networking.ApiRestClient;
import com.example.mumo.parkingapp.utils.PreferenceUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class DashboardActivity extends AppCompatActivity implements TimePickerFragment.OnDataPass {

    private static final String TAG ="DashboardActivity";

    private static final String START_TIME_CODE = "start", CLOSE_TIME_CODE = "close";
    private EditText mLocationEditText, mSlotsEditText, mFeeEditText;
    private TextView mStartTextViewPicker, mCloseTextViewPicker, mStartTextView, mCloseTextView, mErrorTextview;

    private Button mCreateButton;
    private int startTime;
    private int stopTime;

    private ProgressDialog mProgressDialog;

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

        mCreateButton = findViewById(R.id.btn_create);
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
        if (PreferenceUtils.getAccessToken(this).length() <= 0){
            mCreateButton.setEnabled(false);
            mCreateButton.setText("Please Login to Submit");
        }
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createParkingSpace();
            }
        });
//        PreferenceUtils.setAccessToken();
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

    private void createParkingSpace() {
        String location = mLocationEditText.getText().toString();

        int start = startTime;
        int stop = stopTime;
        if (location.length() > 0 && mSlotsEditText.getText().toString().length() > 0
                && mFeeEditText.getText().toString().length() > 0
                && start > 0 && stop > 0) {
            int slots = Integer.valueOf(mSlotsEditText.getText().toString());
            int fee = Integer.valueOf(mFeeEditText.getText().toString());
            //TODO submit request to server

            int user_id = PreferenceUtils.getUserId(this);
            String accessToken = PreferenceUtils.getAccessToken(this);
            RequestParams params = new RequestParams();
            params.put("location",location);
            params.put("start_time",start);
            params.put("end_time", stop);
            params.put("user_id", user_id);
            params.put("slots",slots);
            params.put("fee", fee);

            ApiRestClient.postAuthRequest("/api/parkings/create",params,accessToken,new JsonHttpResponseHandler(){
                @Override
                public void onStart() {
                    super.onStart();
                    String message = "Submitting...";
                    mProgressDialog = ProgressDialog.show(DashboardActivity.this,message, null, true, true);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    Log.i(TAG, "Create successfully: "+ response.toString());
                    mProgressDialog.dismiss();
                    String mesage="";
                    try {
                         mesage=  response.getString("message");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(DashboardActivity.this, mesage, Toast.LENGTH_LONG).show();
                      clearInputs();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    Log.i(TAG, "onFailure: "+ errorResponse.toString());
                }
            });
        } else {
            mErrorTextview.setText(R.string.error_message_null);
        }

    }

    private void clearInputs(){
      mLocationEditText.setText("");
      mFeeEditText.setText("");
      mSlotsEditText.setText("");
    }
}
