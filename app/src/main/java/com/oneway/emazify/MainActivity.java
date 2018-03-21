package com.oneway.emazify;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.emazify.EmazyInitialize;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import Utils.ConnectionDetector;
import Utils.Const;
import Utils.Pref;
import Utils.UserFunctions;


public class MainActivity extends AppCompatActivity {

    private ConnectionDetector mConnectionDetector;
    private UserFunctions mUserFunctions;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    //EmazyInitialize.
}
