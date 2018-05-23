package com.example.mumo.parkingapp.networking;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ApiRestClient {
    private static final String BASE_URL = "http://10.0.3.2:8000";

    private static AsyncHttpClient client = new AsyncHttpClient();


    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("Accept", "application/json");
        client.get(getAbsoluteUrl(url),params,responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
        client.addHeader("Accept", "application/json");
        client.post(getAbsoluteUrl(url),params,responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
