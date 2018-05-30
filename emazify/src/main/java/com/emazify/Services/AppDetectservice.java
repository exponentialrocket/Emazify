package com.emazify.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import Utils.ConnectionDetector;
import Utils.UserFunctions;
import Utils.utils;

public class AppDetectservice extends Service {

    UserFunctions mUserFunctions;
    private static final String TAG = AppDetectservice.class.getSimpleName();
    private ConnectionDetector mConnectionDetector;
    private String accountId;

    @Override
    public void onCreate() {
        super.onCreate();
        mConnectionDetector = new ConnectionDetector(getBaseContext());
        mUserFunctions = new UserFunctions(getBaseContext());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        accountId=(String) intent.getExtras().get("accountId");
        showErrorLog("inside FetchUserCityService accountId"+accountId);

        try {
                    if (mConnectionDetector.isConnectingToInternet()) {
                        callAppDetectApi(getBaseContext(),accountId);

                    } else {
                    stopSelf();
                }

            //  }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return START_STICKY;
    }

    public void callAppDetectApi(final Context context,String accountId) {
        mConnectionDetector = new ConnectionDetector(context);
        mUserFunctions = new UserFunctions(context);
        mUserFunctions.emazifyAppDetect(accountId,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonResult) {
                super.onSuccess(statusCode, headers, jsonResult);
                try {
                    //OWC-2517 #prashantjajal 18-04-2016 011-10-am
                    //implement double click for disable button
                    if (jsonResult != null) {
                        showErrorLog("emazify callAppDetectApi Result==>" + jsonResult.toString());
                    }
                    else {


                    }

                }
                catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);

            }

        });
    }


    private void showErrorLog(String messageString) {
        utils.showErrorLog(TAG, messageString);
    }
}
