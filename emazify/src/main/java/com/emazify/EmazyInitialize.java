package com.emazify;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.emazify.Services.AppDetectservice;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.messaging.RemoteMessage;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;


import org.apache.http.Header;
import org.json.JSONObject;

import java.util.Map;

import Utils.ConnectionDetector;
import Utils.Const;
import Utils.Pref;
import Utils.UserFunctions;
import Utils.utils;
import com.emazify.receivers.MyBroadcastReceiver;

/**
 * Created by owc-android on 15/3/18.
 */

public class EmazyInitialize{

    private ConnectionDetector mConnectionDetector;
    private UserFunctions mUserFunctions;
    private static final String TAG = EmazyInitialize.class.getSimpleName();
    private final int SPLASH_DISPLAY_LENGTH = 5000;
    private final AsyncHttpClient aClient = new SyncHttpClient();
    private static volatile GoogleAnalytics mGoogleAnalytics;
    private static volatile Tracker mTracker;
    private static EmazyInitialize ourInstance = new EmazyInitialize();



    public static EmazyInitialize getInstance() {

        return ourInstance;
    }

    public void callEmazifyLoginApi(final Context context, String accountId,String custId, String mobNo) {
        mConnectionDetector = new ConnectionDetector(context);
        mUserFunctions = new UserFunctions(context);
        mUserFunctions.emazifyLogin(accountId,custId,mobNo,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonResult) {
                super.onSuccess(statusCode, headers, jsonResult);
                try {
                    //OWC-2517 #prashantjajal 18-04-2016 011-10-am
                    //implement double click for disable button
                    if (jsonResult != null) {
                        showErrorLog("emazify login Result==>" + jsonResult.toString());

                        Pref.setValue(context, Const.PREF_EmazyCID,jsonResult.getString("emazyCustomerId"));

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

    public void callDriverLoginApi(final Context context, String accountId,String emazyUCLId, String mobNo,String fullName) {
        mConnectionDetector = new ConnectionDetector(context);
        mUserFunctions = new UserFunctions(context);
        mUserFunctions.emazifyDriverLogin(accountId,emazyUCLId,mobNo,fullName,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonResult) {
                super.onSuccess(statusCode, headers, jsonResult);
                try {
                    //OWC-2517 #prashantjajal 18-04-2016 011-10-am
                    //implement double click for disable button
                    if (jsonResult != null) {
                        showErrorLog("emazify driver login Result==>" + jsonResult.toString());

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

    public void emazifyDriverEvents(final Context context, String accountId,String emazyUCLId,String driverMob,String eventName,String ezSource,Map<String,Object> properties) {
        mConnectionDetector = new ConnectionDetector(context);
        mUserFunctions = new UserFunctions(context);
        mUserFunctions.emazifyDriverEvents(accountId,emazyUCLId,driverMob,eventName,ezSource,properties,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonResult) {
                super.onSuccess(statusCode, headers, jsonResult);
                try {
                    //OWC-2517 #prashantjajal 18-04-2016 011-10-am
                    //implement double click for disable button
                    if (jsonResult != null) {
                        showErrorLog("emazify Driver Events  Result==>" + jsonResult.toString());


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

    public void callEmazifyDriverPropertyApi(final Context context,String accountId, String emazyUCLId,String customAttributeName,boolean customAttributeValue) {
        mConnectionDetector = new ConnectionDetector(context);
        mUserFunctions = new UserFunctions(context);
        mUserFunctions.emazifyDriverProperties(accountId,emazyUCLId,customAttributeName,customAttributeValue,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonResult) {
                super.onSuccess(statusCode, headers, jsonResult);
                try {
                    //OWC-2517 #prashantjajal 18-04-2016 011-10-am
                    //implement double click for disable button
                    if (jsonResult != null) {

                        showErrorLog("emazify Driver Property Result==>"+jsonResult.toString());

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

    public void callEmazifyDriverPropertyApi(final Context context,String accountId, String emazyUCLId,String customAttributeName,float customAttributeValue) {
        mConnectionDetector = new ConnectionDetector(context);
        mUserFunctions = new UserFunctions(context);
        mUserFunctions.emazifyDriverProperties(accountId,emazyUCLId,customAttributeName,customAttributeValue,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonResult) {
                super.onSuccess(statusCode, headers, jsonResult);
                try {
                    //OWC-2517 #prashantjajal 18-04-2016 011-10-am
                    //implement double click for disable button
                    if (jsonResult != null) {

                        showErrorLog("emazify Driver Property Result==>"+jsonResult.toString());

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

    public void callEmazifyDriverPropertyApi(final Context context,String accountId, String emazyUCLId,String customAttributeName,int customAttributeValue) {
        mConnectionDetector = new ConnectionDetector(context);
        mUserFunctions = new UserFunctions(context);
        mUserFunctions.emazifyDriverProperties(accountId,emazyUCLId,customAttributeName,customAttributeValue,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonResult) {
                super.onSuccess(statusCode, headers, jsonResult);
                try {
                    //OWC-2517 #prashantjajal 18-04-2016 011-10-am
                    //implement double click for disable button
                    if (jsonResult != null) {

                        showErrorLog("emazify Driver Property Result==>"+jsonResult.toString());

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

    public void callEmazifyDriverPropertyApi(final Context context,String accountId, String emazyUCLId,String customAttributeName,String  customAttributeValue) {
        mConnectionDetector = new ConnectionDetector(context);
        mUserFunctions = new UserFunctions(context);
        mUserFunctions.emazifyDriverProperties(accountId,emazyUCLId,customAttributeName,customAttributeValue,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonResult) {
                super.onSuccess(statusCode, headers, jsonResult);
                try {
                    //OWC-2517 #prashantjajal 18-04-2016 011-10-am
                    //implement double click for disable button
                    if (jsonResult != null) {

                        showErrorLog("emazify Driver Property Result==>"+jsonResult.toString());

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

    public void callEmazifyDriverPropertyApi(final Context context,String accountId, String emazyUCLId,String customAttributeName,long customAttributeValue) {
        mConnectionDetector = new ConnectionDetector(context);
        mUserFunctions = new UserFunctions(context);
        mUserFunctions.emazifyDriverProperties(accountId,emazyUCLId,customAttributeName,customAttributeValue,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonResult) {
                super.onSuccess(statusCode, headers, jsonResult);
                try {
                    //OWC-2517 #prashantjajal 18-04-2016 011-10-am
                    //implement double click for disable button
                    if (jsonResult != null) {

                        showErrorLog("emazify Driver Property Result==>"+jsonResult.toString());

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

    public void callEmazifyDriverPropertyApi(final Context context,String accountId, String emazyUCLId,String customAttributeName,double customAttributeValue) {
        mConnectionDetector = new ConnectionDetector(context);
        mUserFunctions = new UserFunctions(context);
        mUserFunctions.emazifyDriverProperties(accountId,emazyUCLId,customAttributeName,customAttributeValue,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonResult) {
                super.onSuccess(statusCode, headers, jsonResult);
                try {
                    //OWC-2517 #prashantjajal 18-04-2016 011-10-am
                    //implement double click for disable button
                    if (jsonResult != null) {

                        showErrorLog("emazify Driver Property Result==>"+jsonResult.toString());

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

    public boolean isEmazifyNotification(final Context context,@NonNull RemoteMessage msg,String accountId,String customerId) {

        Map<String, String> receivedMap = msg.getData();

        if(receivedMap.get("key").equals("campaignNotification") || receivedMap.get("key").equals("silent")){
           // sendNotification(context,"Ahmedabad",customerId,accountId,msg);
            return true;
        }

        return false;
    }

    public void sendNotification(final Context context,String userCity,String customerId,String accountId, RemoteMessage msg) {


        //Toast.makeText(context,"INSIDE SEND NOTI",Toast.LENGTH_LONG).show();
        Log.e("Emazify","INSIDE SEND NOTI");

        try {
            mGoogleAnalytics = GoogleAnalytics.getInstance(context);
            mGoogleAnalytics.setLocalDispatchPeriod(1);
            mTracker = mGoogleAnalytics.newTracker(Const.STAGING_GA_TRACKING_ID);
            mTracker.enableExceptionReporting(true);
            mTracker.enableAdvertisingIdCollection(true);
            mTracker.enableAutoActivityTracking(false);
        }catch (Exception e){
            e.printStackTrace();
            Log.e("Emazify","EXCEPTION");
        }

        try{
            if (mConnectionDetector.isConnectingToInternet()) {


                Map<String, String> receivedMap = msg.getData();

                if (receivedMap.get("key").equals("silent")) {

                    mTracker.set("&uid", customerId);
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory(customerId)
                            .setAction("Emazify silent notification")
                            .setLabel("Inside Emazify SDK").build());

                    //call get user current location and get user current city name in background service
                    Intent appDetectService = new Intent(context, AppDetectservice.class);
                    appDetectService.putExtra("accountId", accountId);
                    appDetectService.putExtra("userCity", userCity);
                    appDetectService.putExtra("customerId", customerId);
                    showErrorLog("moving AppDetectService accountId" + accountId);
                    context.startService(appDetectService);
                    String message = receivedMap.get("message");
                    int requestID = (int) System.currentTimeMillis();

                    Intent intent1 = new Intent(context, MyBroadcastReceiver.class);

                    PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, 99999+requestID, intent1, 0);

                    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle().bigText(message);
                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_notification)
                            .setContentTitle(context.getResources().getString(R.string.app_name))
                            .setContentText(message)
                            .setAutoCancel(false)
                            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                            .setStyle(bigTextStyle)
                            .setSound(defaultSoundUri)
                            .setContentIntent(pendingIntent1);

                    notificationBuilder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(requestID , notificationBuilder.build());
                    return;


                }

                if (receivedMap.get("key").equals("campaignNotification")) {

                    //call get user current location and get user current city name in background service
                    String message = receivedMap.get("message");
                    int requestID = (int) System.currentTimeMillis();

                    Intent intent1 = new Intent(context, MyBroadcastReceiver.class);

                    PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, 99999+requestID, intent1, 0);

                    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle().bigText(message);
                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_notification)
                            .setContentTitle(context.getResources().getString(R.string.app_name))
                            .setContentText(message)
                            .setAutoCancel(false)
                            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                            .setStyle(bigTextStyle)
                            .setSound(defaultSoundUri)
                            .setContentIntent(pendingIntent1);

                    notificationBuilder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
                    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(requestID , notificationBuilder.build());
                    return;

                }
            }
        }catch (Exception e){

        }





    }

    public void callAutoSystemUserPropertyApi(final Context context,String accountId, String custId, String mobNo, String email,
                                              String fcmToken, Boolean ezPushNotiEnabled, String latLng) {
        mConnectionDetector = new ConnectionDetector(context);
        mUserFunctions = new UserFunctions(context);
        Pref.setValue(context, Const.GCM_TOKEN, fcmToken);
        mUserFunctions.emazifyAutoSystemUserProperty(accountId,custId,mobNo,email,fcmToken,ezPushNotiEnabled,latLng,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonResult) {
                super.onSuccess(statusCode, headers, jsonResult);
                try {
                    //OWC-2517 #prashantjajal 18-04-2016 011-10-am
                    //implement double click for disable button
                    if (jsonResult != null) {
                        showErrorLog("emazify autoSystemProperty Result==>" + jsonResult.toString());

                        Pref.setValue(context, Const.PREF_EmazyCID,jsonResult.getString("emazyCustomerId"));
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

    public void callEmazifyUserPropertyApi(final Context context,String accountId, String custId,String customAttributeName,Boolean customAttributeValue) {
        mConnectionDetector = new ConnectionDetector(context);
        mUserFunctions = new UserFunctions(context);
        mUserFunctions.emazifyUserProperty(accountId,custId,customAttributeName,customAttributeValue,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonResult) {
                super.onSuccess(statusCode, headers, jsonResult);
                try {
                    //OWC-2517 #prashantjajal 18-04-2016 011-10-am
                    //implement double click for disable button
                    if (jsonResult != null) {

                        showErrorLog("emazify UserProperty Result==>"+jsonResult.toString());

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

    public void callEmazifyUserPropertyApi(final Context context,String accountId, String custId,String customAttributeName,String customAttributeValue) {
        mConnectionDetector = new ConnectionDetector(context);
        mUserFunctions = new UserFunctions(context);
        mUserFunctions.emazifyUserProperty(accountId,custId,customAttributeName,customAttributeValue,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonResult) {
                super.onSuccess(statusCode, headers, jsonResult);
                try {
                    //OWC-2517 #prashantjajal 18-04-2016 011-10-am
                    //implement double click for disable button
                    if (jsonResult != null) {

                        showErrorLog("emazify UserProperty Result==>"+jsonResult.toString());

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

    public void callEmazifyUserPropertyApi(final Context context,String accountId, String custId,String customAttributeName,float customAttributeValue) {
        mConnectionDetector = new ConnectionDetector(context);
        mUserFunctions = new UserFunctions(context);
        mUserFunctions.emazifyUserProperty(accountId,custId,customAttributeName,customAttributeValue,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonResult) {
                super.onSuccess(statusCode, headers, jsonResult);
                try {
                    //OWC-2517 #prashantjajal 18-04-2016 011-10-am
                    //implement double click for disable button
                    if (jsonResult != null) {

                        showErrorLog("emazify UserProperty Result==>"+jsonResult.toString());

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

    public void callEmazifyUserPropertyApi(final Context context,String accountId, String custId,String customAttributeName,int customAttributeValue) {
        mConnectionDetector = new ConnectionDetector(context);
        mUserFunctions = new UserFunctions(context);
        mUserFunctions.emazifyUserProperty(accountId,custId,customAttributeName,customAttributeValue,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonResult) {
                super.onSuccess(statusCode, headers, jsonResult);
                try {
                    //OWC-2517 #prashantjajal 18-04-2016 011-10-am
                    //implement double click for disable button
                    if (jsonResult != null) {

                        showErrorLog("emazify UserProperty Result==>"+jsonResult.toString());

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

    public void callEmazifyUserPropertyApi(final Context context,String accountId, String custId,String customAttributeName,double customAttributeValue) {
        mConnectionDetector = new ConnectionDetector(context);
        mUserFunctions = new UserFunctions(context);
        mUserFunctions.emazifyUserProperty(accountId,custId,customAttributeName,customAttributeValue,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonResult) {
                super.onSuccess(statusCode, headers, jsonResult);
                try {
                    //OWC-2517 #prashantjajal 18-04-2016 011-10-am
                    //implement double click for disable button
                    if (jsonResult != null) {

                        showErrorLog("emazify UserProperty Result==>"+jsonResult.toString());

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

    public void callEmazifyUserPropertyApi(final Context context,String accountId, String custId,String customAttributeName,long customAttributeValue) {
        mConnectionDetector = new ConnectionDetector(context);
        mUserFunctions = new UserFunctions(context);
        mUserFunctions.emazifyUserProperty(accountId,custId,customAttributeName,customAttributeValue,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonResult) {
                super.onSuccess(statusCode, headers, jsonResult);
                try {
                    //OWC-2517 #prashantjajal 18-04-2016 011-10-am
                    //implement double click for disable button
                    if (jsonResult != null) {

                        showErrorLog("emazify UserProperty Result==>"+jsonResult.toString());

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

    public void callEmazifyLogOutApi(final Context context,String accountId, String custId,String mobNo) {
        mConnectionDetector = new ConnectionDetector(context);
        mUserFunctions = new UserFunctions(context);
        mUserFunctions.emazifyLogOut(accountId,custId,mobNo,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonResult) {
                super.onSuccess(statusCode, headers, jsonResult);
                try {
                    //OWC-2517 #prashantjajal 18-04-2016 011-10-am
                    //implement double click for disable button
                    if (jsonResult != null) {
                        showErrorLog("emazify Logout  Result==>" + jsonResult.toString());


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

    public void emazifyEvents(final Context context, String accountId,String custId,String eventName,Map<String,Object> properties) {
        mConnectionDetector = new ConnectionDetector(context);
        mUserFunctions = new UserFunctions(context);
        mUserFunctions.emazifyEvents(accountId,custId,eventName,properties,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonResult) {
                super.onSuccess(statusCode, headers, jsonResult);
                try {
                    //OWC-2517 #prashantjajal 18-04-2016 011-10-am
                    //implement double click for disable button
                    if (jsonResult != null) {
                        showErrorLog("emazify Events  Result==>" + jsonResult.toString());


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
