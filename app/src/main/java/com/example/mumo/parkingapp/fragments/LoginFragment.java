package com.example.mumo.parkingapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mumo.parkingapp.DashboardActivity;
import com.example.mumo.parkingapp.R;
import com.example.mumo.parkingapp.networking.ApiRestClient;
import com.example.mumo.parkingapp.utils.PreferenceUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginFragment extends Fragment {
    private EditText  mEmailEditText, mPasswordEditText;
    private Button mLoginButton;
    private TextView mErrorTextView;
    private static final String TAG ="LoginFragment";
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
                    RequestParams params = new RequestParams();
                    params.put("email",email);
                    params.put("password", password);

                    ApiRestClient.post("/api/login",params, new JsonHttpResponseHandler(){
                        @Override
                        public void onStart() {
                            super.onStart();
                            mLoginButton.setEnabled(false);
                            mLoginButton.setText("Logging in...");
                            mLoginButton.setBackgroundColor(getResources().getColor(R.color.disableBlurButton));
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            Log.i(TAG, "success login: "+ response.toString());
                            try {
                                final String accessToken = response.getString("access_token");
                                final String refreshToken = response.getString("refresh_token");
//                                PreferenceUtils.setAccessToken(getActivity(),accessToken);
                                ApiRestClient.getAuthRequest("/api/user",null,accessToken,new JsonHttpResponseHandler(){
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                        super.onSuccess(statusCode, headers, response);
                                        Log.i(TAG, "on success user details: "+ response.toString());

                                        try {
                                            int userId = response.getInt("id");
                                            String userName = response.getString("name");
                                            PreferenceUtils.setAccessToken(getActivity(), accessToken);
                                            PreferenceUtils.setRefreshToken(getActivity(), refreshToken);
                                            PreferenceUtils.setUserId(getActivity(), userId);
                                            PreferenceUtils.setUserName(getActivity(), userName);
                                            Toast.makeText(getActivity(), "Successfully logged in", Toast.LENGTH_LONG).show();
                                            Intent i = new Intent(getActivity(), DashboardActivity.class);
                                            startActivity(i);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } catch (JSONException e) {

                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                            Log.i(TAG, "error logging" + errorResponse.toString());
                            try {
                                String errorMessage = errorResponse.getString("message");
                                mErrorTextView.setText(errorMessage);
                                resetButton();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }else {
                    mErrorTextView.setText(R.string.error_message_null);
                }
            }
        });
        return view;
    }

    private void resetButton(){
        mLoginButton.setEnabled(true);
        mLoginButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mLoginButton.setText("LOGIN");
    }
//    private
}
