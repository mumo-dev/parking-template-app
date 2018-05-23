package com.example.mumo.parkingapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mumo.parkingapp.R;

public class LoginFragment extends Fragment {
    private EditText  mEmailEditText, mPasswordEditText;
    private Button mLoginButton;
    private TextView mErrorTextView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mEmailEditText = view.findViewById(R.id.user_email);
        mPasswordEditText = view.findViewById(R.id.user_password);
        mLoginButton = view.findViewById(R.id.btn_login);
        mErrorTextView = view.findViewById(R.id.tv_errors);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                if (email.length() >0 && password.length() >0){
                    //TODO submit data to server for validation
                }else {
                    mErrorTextView.setText(R.string.error_message_null);
                }
            }
        });
        return view;
    }
}
