package com.emazify;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import Utils.ConnectionDetector;
import Utils.Const;
import Utils.Pref;
import Utils.UserFunctions;
import Utils.utils;
import receivers.MyBroadcastReceiver;

import static Utils.Const.PREF_EmazyCID;

/**
 * Created by owc-android on 15/3/18.
 */

public class EmazyInitialize{

    private ConnectionDetector mConnectionDetector;
    private UserFunctions mUserFunctions;
    private static final String TAG = EmazyInitialize.class.getSimpleName();
    private final int SPLASH_DISPLAY_LENGTH = 5000;
    private final AsyncHttpClient aClient = new SyncHttpClient();
    private static String EMAZIFY_APP_DETECT_URL = "https://dsr8v0potg.execute-api.ap-south-1.amazonaws.com/update_app_detected";
    private static String emaziCustId = "";
    private static String EMAZIFY_APP_DETECT_KEY= "EKVa1qoLNc47qTzAoDHmQ6itfa2Eqq1249LQY5dM";

    private static EmazyInitialize ourInstance = new EmazyInitialize();

    public static EmazyInitialize getInstance() {
        return ourInstance;
    }

    public void callEmazifyLoginApi(final Context context, String custId) {
        mConnectionDetector = new ConnectionDetector(context);
        mUserFunctions = new UserFunctions(context);
        mUserFunctions.emazifyLogin(custId,new JsonHttpResponseHandler() {
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




    public void sendNotification(final Context context, RemoteMessage msg) {
/*

        Intent intent = new Intent(this, .class);

        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
*/
        Map<String, String> receivedMap = msg.getData();

        if(receivedMap.get("key").equals("silent")){
                callAppDetectApi(context);
        return;
        }

        String message = receivedMap.get("message");


        //OWC-960 #vijayrajput 03-12-2015 02-00-pm
        //fix issue on multiple notification click and ride detail not load properly
        int requestID = (int) System.currentTimeMillis();
      //  PendingIntent pendingIntent = PendingIntent.getActivity(context, requestID /* Request code */, new Intent() , PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);

        Intent intent = new Intent(context, MyBroadcastReceiver.class);
        Intent intent1 = new Intent(context, MyBroadcastReceiver.class);
        intent.putExtra("campaignDataUniqueId",receivedMap.get("campaignDataUniqueId"));
        intent.putExtra("notiClear","clear");
        intent1.putExtra("campaignDataUniqueId",receivedMap.get("campaignDataUniqueId"));
        intent1.putExtra("notiClick","click");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestID, intent, 0);
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

        notificationBuilder.setDeleteIntent(pendingIntent);
        notificationBuilder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(requestID/* ID of notification */, notificationBuilder.build());



    }

    public void callAppDetectApi(final Context context) {
        mConnectionDetector = new ConnectionDetector(context);
        //mUserFunctions = new UserFunctions(context);
        RequestParams jsonParams = new RequestParams();

        try{
            emaziCustId = Pref.getValue(context, PREF_EmazyCID,"");

        }catch (NullPointerException e){

        }catch (Exception e){

        }

        jsonParams.put("accountId", "onewaycab");
        jsonParams.put("appVersion", utils.getAppVersion(context));
        jsonParams.put("pushNotificationEnabled", "1");
        jsonParams.put("imei", utils.getDeviceIMEI(context));
        jsonParams.put("emazyCustomerId", emaziCustId);
        aClient.addHeader("X-Api-Key", EMAZIFY_APP_DETECT_KEY);

        aClient.post(EMAZIFY_APP_DETECT_URL,jsonParams, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
              //  sendBroadcast(new Intent(IntentServiceSample.ACTION_START));
                Log.e("EMAZIFY", "onStart");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.e("EMAZIFY","onSuccess");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Log.e("EMAZIFY", "onFailure");
            }

            @Override
            public void onCancel() {
                Log.e("EMAZIFY", "onCancel");
            }

            @Override
            public void onRetry(int retryNo) {
                Log.e("EMAZIFY", String.format("onRetry: %d", retryNo));
            }

            @Override
            public void onFinish() {
                Log.e("EMAZIFY", "onFinish");
            }
        });
    }

    /*    mUserFunctions.emazifyAppDetect(new JsonHttpResponseHandler() {
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

        });*/


    public void callAutoSystemUserPropertyApi(final Context context, String custId, String mobNo, String email,
                                              String fcmToken, String ezPushNotiEnabled, String latLng) {
        mConnectionDetector = new ConnectionDetector(context);
        mUserFunctions = new UserFunctions(context);
        Pref.setValue(context, Const.GCM_TOKEN, fcmToken);
        mUserFunctions.emazifyAutoSystemUserProperty(custId,mobNo,email,fcmToken,ezPushNotiEnabled,latLng,new JsonHttpResponseHandler() {
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


    public void callEmazifyUserPropertyApi(final Context context, String custId,String customAttributeName,String customAttributeValue) {

        mUserFunctions.emazifyUserProperty(custId,customAttributeName,customAttributeValue,new JsonHttpResponseHandler() {
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

    public void callEmazifyUserPropertyApi(final Context context, String custId,String customAttributeName,float customAttributeValue) {

        mUserFunctions.emazifyUserProperty(custId,customAttributeName,customAttributeValue,new JsonHttpResponseHandler() {
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

    public void callEmazifyUserPropertyApi(final Context context, String custId,String customAttributeName,int customAttributeValue) {

        mUserFunctions.emazifyUserProperty(custId,customAttributeName,customAttributeValue,new JsonHttpResponseHandler() {
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

    public void callEmazifyUserPropertyApi(final Context context, String custId,String customAttributeName,double customAttributeValue) {

        mUserFunctions.emazifyUserProperty(custId,customAttributeName,customAttributeValue,new JsonHttpResponseHandler() {
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

    public void callEmazifyUserPropertyApi(final Context context, String custId,String customAttributeName,long customAttributeValue) {

        mUserFunctions.emazifyUserProperty(custId,customAttributeName,customAttributeValue,new JsonHttpResponseHandler() {
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
    public void callEmazifyLogOutApi(final Context context, String custId) {
        mUserFunctions.emazifyLogOut(custId,new JsonHttpResponseHandler() {
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

    public void emazifyEvents(final Context context, String custId,String eventName,Map<String,Object> properties) {
        mUserFunctions.emazifyEvents(custId,eventName,properties,new JsonHttpResponseHandler() {
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
