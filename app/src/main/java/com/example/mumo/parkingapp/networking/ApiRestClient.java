package com.example.mumo.parkingapp.networking;

import com.example.mumo.parkingapp.utils.PreferenceUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ApiRestClient {
//    private static final String BASE_URL = "http://10.0.3.2:8000";
    private static final String BASE_URL = "https://whispering-brushlands-27448.herokuapp.com/";

    private static AsyncHttpClient client = new AsyncHttpClient();


    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("Accept", "application/json");
        client.get(getAbsoluteUrl(url),params,responseHandler);
    }
    public static void getAuthRequest(String url, RequestParams params,String token, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("Accept", "application/json");
        client.addHeader("Authorization","Bearer "+token);
        client.get(getAbsoluteUrl(url),params,responseHandler);
    }



    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler){
        client.addHeader("Accept", "application/json");
        client.post(getAbsoluteUrl(url),params,responseHandler);
    }

    public static void postAuthRequest(String url, RequestParams params,String token, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("Accept", "application/json");
        client.addHeader("Authorization","Bearer "+token);
        client.post(getAbsoluteUrl(url),params,responseHandler);
    }
    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
