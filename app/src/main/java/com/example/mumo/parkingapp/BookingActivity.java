package com.example.mumo.parkingapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mumo.parkingapp.model.Parking;
import com.example.mumo.parkingapp.networking.ApiRestClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class BookingActivity extends AppCompatActivity {

    private static final String EXTRA_BOOKING = "com.example.mumo.parkingapp.booking";
    private static final String TAG ="BookingActivity";

    private Spinner mSpinner;
    private TextView mLocationTextView;
    private TextView mFeeTextView;
    private TextView mErrorTextView;
    private Button mBookButton;

    private ProgressDialog mProgressDialog;
    public static Intent newIntent(Context packageContext, Parking parking){
        Intent intent = new Intent(packageContext, BookingActivity.class);
        intent.putExtra(EXTRA_BOOKING, parking);
        return  intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=  null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        final Parking parking = (Parking) getIntent().getSerializableExtra(EXTRA_BOOKING);

        mLocationTextView = findViewById(R.id.parking_loc);
        mLocationTextView.setText(parking.getLocation());

        mFeeTextView = findViewById(R.id.parking_fee);
        mFeeTextView.setText(String.valueOf(parking.getFee()));

        String[] timeSlots = parking.getTimeSlots();
        ArrayAdapter<String> timeSlotAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line,timeSlots);
        mSpinner = findViewById(R.id.time_spinner);
        mSpinner.setAdapter(timeSlotAdapter);

        /*TextView sel_view = (TextView)spinner.getSelectedView();
        String value = sel_view.getText().toString();*/

        mErrorTextView = findViewById(R.id.tv_errors);
        mBookButton = findViewById(R.id.btn_book);
        mBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int parking_id = parking.getId();
                TextView sel_view = (TextView)mSpinner.getSelectedView();
                String value = sel_view.getText().toString();
//                mErrorTextView.setText("value choosen "+value+": "+parking_id);
                RequestParams params = new RequestParams();
                params.put("parking_id",parking_id);
                params.put("time", value);
                ApiRestClient.post("/api//parkings/book",params,new JsonHttpResponseHandler(){
                    @Override
                    public void onStart() {
                        super.onStart();
                        mProgressDialog =
                                ProgressDialog.show(BookingActivity.this, "Booking ...", null, true, true);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        mProgressDialog.dismiss();
                        if (response.has("ref_no")) {
                            try {

                                String ref_no = response.getString("ref_no");
                                String time = response.getString("time");
                                int slotId = response.getInt("slot_id");
                                String message =" You have successful booked your space";
                                Toast.makeText(BookingActivity.this,message,Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else if (response.has("error")){
                            try {
                                mErrorTextView.setText(response.getString("error"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.i(TAG, "onSuccess: "+response.toString());
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        mProgressDialog.dismiss();
                        Log.i(TAG, "onFailure: ");
                        mErrorTextView.setText(throwable.getMessage());
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
