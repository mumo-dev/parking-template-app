package com.example.mumo.parkingapp.fragments;

import android.content.Context;
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
import com.example.mumo.parkingapp.networking.ApiRestClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class RegisterFragment extends Fragment {

    private EditText mNameEditText, mEmailEditText, mPasswordEditText, mPasswordConfirmationEditText;
    private Button mRegisterButton;
    private TextView mErrorsTextView;
    private static final String TAG = "RegisterFragment";

    private static final String REGISTER_URL = "http://10.0.3.2:8000/api/register/";//http://10.0.3.2:8000/

    private IViewPagerSwitcher mIViewPagerSwitcher;

    public interface IViewPagerSwitcher{
        void switchToPage(int newPagePosition);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mIViewPagerSwitcher = (IViewPagerSwitcher)context;
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
                    RequestParams params = new RequestParams();
                    params.put("name", name);
                    params.put("email", email);
                    params.put("password", password);
                    params.put("password_confirmation",password_confirmation);

                    ApiRestClient.post("/api/register",params,new JsonHttpResponseHandler(){
                        @Override
                        public void onStart() {
                            super.onStart();
                            mRegisterButton.setEnabled(false);
                            mRegisterButton.setBackgroundColor(getResources().getColor(R.color.disableBlurButton));
                            mRegisterButton.setText("Submitting...");
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            Log.i(TAG, "onSuccess called:  "+ response.toString());
                            try {
                                String successMessage = response.getString("message");
                                Snackbar.make(view, successMessage,Snackbar.LENGTH_LONG).show();
                                clearInputs();
                                resetButton();
                                mIViewPagerSwitcher.switchToPage(0);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }



                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            Log.i(TAG, "onFailure Json response:"+ errorResponse.toString());

                            try {
                                JSONObject errorArray = errorResponse.getJSONObject("errors");
                                JSONArray emailerror =errorArray.getJSONArray("email");
                                String error = emailerror.getString(0);
                                mErrorsTextView.setText(error);
                                resetButton();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });


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
        mRegisterButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mRegisterButton.setText("create account");
    }
}
