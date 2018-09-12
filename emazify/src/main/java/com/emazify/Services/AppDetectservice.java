package com.emazify.Services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.emazify.MyApplication1;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import Utils.ConnectionDetector;
import Utils.UserFunctions;
import Utils.utils;

public class AppDetectservice extends IntentService {

    UserFunctions mUserFunctions;
    private static final String TAG = AppDetectservice.class.getSimpleName();
    private ConnectionDetector mConnectionDetector;
    private String accountId;
    private String userCity;
    private String customerId;
    private Tracker mTracker;

    public AppDetectservice() {
        super("AppDetectservice");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mConnectionDetector = new ConnectionDetector(getBaseContext());
        mUserFunctions = new UserFunctions(getBaseContext());
        mTracker = MyApplication1.tracker();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        accountId=(String) intent.getExtras().get("accountId");
        userCity = (String) intent.getExtras().get("userCity");
        customerId = (String) intent.getExtras().get("customerId");

        showErrorLog("inside AppDetectservice accountId"+accountId);

        try {
                    if (mConnectionDetector.isConnectingToInternet()) {

                        callAppDetectApi(getBaseContext(),accountId,customerId,userCity);

                    } else {
                    stopSelf();
                }

            //  }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return START_STICKY;
    }

    public void callAppDetectApi(final Context context, String accountId, final String customerId, String userCity) {
        mConnectionDetector = new ConnectionDetector(context);
        mUserFunctions = new UserFunctions(context);

        mTracker.set("&uid", customerId);
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Emazify")
                .setAction("AppDetect call start")
                .setLabel("silent").build());

        mUserFunctions.emazifyAppDetect(accountId,userCity,new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonResult) {
                super.onSuccess(statusCode, headers, jsonResult);
                try {
                    //OWC-2517 #prashantjajal 18-04-2016 011-10-am
                    //implement double click for disable button
                    if (jsonResult != null) {
                        mTracker.set("&uid", customerId);
                        mTracker.send(new HitBuilders.EventBuilder()
                                .setCategory("Emazify")
                                .setAction("AppDetect call success")
                                .setLabel("SUCCESS").build());
                        showErrorLog("emazify callAppDetectApi Result==>" + jsonResult.toString());
                    }
                    else {

                        mTracker.set("&uid", customerId);
                        mTracker.send(new HitBuilders.EventBuilder()
                                .setCategory("Emazify")
                                .setAction("AppDetect call fail")
                                .setLabel("FAIL").build());
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
                mTracker.set("&uid", customerId);
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Emazify")
                        .setAction("AppDetect call fail")
                        .setLabel("FAIL").build());
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                mTracker.set("&uid", customerId);
                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Emazify")
                        .setAction("AppDetect call fail")
                        .setLabel("FAIL").build());
            }

        });
    }


    private void showErrorLog(String messageString) {
        utils.showErrorLog(TAG, messageString);
    }
}
