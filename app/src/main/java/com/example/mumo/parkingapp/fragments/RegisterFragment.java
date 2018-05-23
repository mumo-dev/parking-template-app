package com.example.mumo.parkingapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mumo.parkingapp.R;


public class RegisterFragment extends Fragment {

    private EditText mNameEditText, mEmailEditText, mPasswordEditText, mPasswordConfirmationEditText;
    private Button mRegisterButton;
    private TextView mErrorsTextView;
    private static final String TAG = "RegisterFragment";

    private static final String REGISTER_URL = "http://10.0.3.2:8000/api/register/";//http://10.0.3.2:8000/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R
                .layout.fragment_register, container, false);
        mNameEditText = view.findViewById(R.id.user_name);
        mEmailEditText = view.findViewById(R.id.user_email);
        mPasswordEditText = view.findViewById(R.id.user_password);
        mPasswordConfirmationEditText = view.findViewById(R.id.user_password_confirmation);
        mRegisterButton = view.findViewById(R.id.btn_register);
        mErrorsTextView = view.findViewById(R.id.tv_errors);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = mNameEditText.getText().toString();
                final String email = mEmailEditText.getText().toString();
                final String password = mPasswordEditText.getText().toString();
                final String password_confirmation = mPasswordConfirmationEditText.getText().toString();
                if (name.length() > 0 && email.length() > 0 && password.length() > 0
                        && password.equals(password_confirmation)) {
                    //TODO submit data to server



                } else {
                    mErrorsTextView.setText(R.string.error_message_null);
                }
            }
        });
        return view;
    }

    private void clearInputs() {
        mNameEditText.setText("");
        mEmailEditText.setText("");
        mPasswordEditText.setText("");
        mPasswordConfirmationEditText.setText("");

    }

    private void resetButton() {
        mRegisterButton.setEnabled(true);
        mRegisterButton.setText("create account");
    }
}
