package com.oneway.emazify;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import emazify.oneway.com.onewayuser.onewayuser;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onewayuser.getInstance().makeMeAwesome(MainActivity.this,"");

    }
}
