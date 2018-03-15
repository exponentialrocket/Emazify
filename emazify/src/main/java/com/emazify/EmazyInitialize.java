package com.emazify;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import Utils.ConnectionDetector;
import Utils.Const;
import Utils.Pref;
import Utils.UserFunctions;
import Utils.utils;

/**
 * Created by owc-android on 15/3/18.
 */

public class EmazyInitialize {

    private ConnectionDetector mConnectionDetector;
    private UserFunctions mUserFunctions;
    private static final String TAG = EmazyInitialize.class.getSimpleName();

    private static EmazyInitialize ourInstance = new EmazyInitialize();

    public static EmazyInitialize getInstance() {
        return ourInstance;
    }

    private EmazyInitialize() {


    }

    public void callEmazifyLoginApi(final Context context, String CId) {
        mConnectionDetector = new ConnectionDetector(context);
        mUserFunctions = new UserFunctions(context);
        String CId1 = CId;
        mUserFunctions.emazifyLogin(CId1,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonResult) {
                super.onSuccess(statusCode, headers, jsonResult);
                try {
                    //OWC-2517 #prashantjajal 18-04-2016 011-10-am
                    //implement double click for disable button
                    if (jsonResult != null) {
                        showErrorLog("emazify Result==>" + jsonResult.toString());

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


    private void showErrorLog(String messageString) {
        utils.showErrorLog(TAG, messageString);
    }

}
