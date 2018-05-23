package com.example.mumo.parkingapp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import com.example.mumo.parkingapp.R;

public class TimePickerFragment extends DialogFragment {

    private static final String  ARG_CODE = "code";
    public static TimePickerFragment newInstance(String code){
        Bundle args = new Bundle();
        args.putString(ARG_CODE, code);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public interface OnDataPass {
        void getTimePicked(int hour,int minute, String code);
    }

    private TimePicker mTimePicker;

    private OnDataPass dataPasser;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String code = getArguments().getString(ARG_CODE);
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null);
        mTimePicker = v.findViewById(R.id.time_picker);
        mTimePicker.setIs24HourView(true);
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int hour = mTimePicker.getCurrentHour();
                        int minute = mTimePicker.getCurrentMinute();

                        dataPasser.getTimePicked(hour, minute, code);
                    }
                })
                .create();
    }

}
