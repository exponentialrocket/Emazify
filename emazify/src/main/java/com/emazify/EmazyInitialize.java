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

import com.google.firebase.messaging.RemoteMessage;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;


import org.apache.http.Header;
import org.json.JSONObject;

import java.util.Map;

import com.emazify.Services.FetchUserCityService;
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

    public boolean isEmazifyNotification(final Context context,@NonNull RemoteMessage msg) {

        Map<String, String> receivedMap = msg.getData();

        if(receivedMap.get("key").equals("campaignNotification") || receivedMap.get("key").equals("silent")){

            return true;
        }

        return false;
    }

    public void sendNotification(final Context context,String accountId) {

                        Intent intentFetchUserCityService = new Intent(context, FetchUserCityService.class);
                        intentFetchUserCityService.putExtra("accountId",accountId);
                        showErrorLog("moving FetchUserCityService accountId"+accountId);
                        context.startService(intentFetchUserCityService);
                        return;

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
