package com.emazify;

import android.app.Activity;
import android.content.Context;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import Utils.ConnectionDetector;
import Utils.Const;
import Utils.Pref;
import Utils.UserFunctions;
import Utils.utils;

/**
 * Created by owc-android on 15/3/18.
 */

public abstract class EmazyInitialize {

    private ConnectionDetector mConnectionDetector;
    private UserFunctions mUserFunctions;
    private static final String TAG = EmazyInitialize.class.getSimpleName();


    abstract void callEmazifyLoginApi();

    abstract void callAutoSystemUserPropertyApi();

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

    public void callAutoSystemUserPropertyApi(final Context context, String custId,String mobNo,String emailId,String fcmToken,String ezPushNotiEnabled) {
        mConnectionDetector = new ConnectionDetector(context);
        mUserFunctions = new UserFunctions(context);
        mUserFunctions.emazifyAutoSystemUserProperty(custId,mobNo,emailId,fcmToken,ezPushNotiEnabled,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonResult) {
                super.onSuccess(statusCode, headers, jsonResult);
                try {
                    //OWC-2517 #prashantjajal 18-04-2016 011-10-am
                    //implement double click for disable button
                    if (jsonResult != null) {
                        showErrorLog("emazify callAutoSystemUserProperty Result==>" + jsonResult.toString());

                        Pref.setValue(context,Const.PREF_EmazyCID,jsonResult.getString("emazyCustomerId"));

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

    public void callEmazifyUserPropertyApi(final Context context, String custId) {
        mUserFunctions.emazifyUserProperty(custId,new JsonHttpResponseHandler() {
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


    private void showErrorLog(String messageString) {
        utils.showErrorLog(TAG, messageString);
    }

}
