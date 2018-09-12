package com.emazify;

import android.app.Application;
import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import Utils.Const;

public class trackingFile extends Application{
    //object variables
    private static volatile GoogleAnalytics mGoogleAnalytics;
    private static volatile Tracker mTracker;

    public static GoogleAnalytics analytics() {
        return mGoogleAnalytics;
    }

    public static Tracker tracker() {
        return mTracker;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //OWC-1651 #vijayrajput 08-01-2016 03-00-pm
        //integrate branch.io in app
        mGoogleAnalytics = GoogleAnalytics.getInstance(this);
        mGoogleAnalytics.setLocalDispatchPeriod(1);
        mTracker = mGoogleAnalytics.newTracker(Const.STAGING_GA_TRACKING_ID);
        mTracker.enableExceptionReporting(true);
        mTracker.enableAdvertisingIdCollection(true);
        mTracker.enableAutoActivityTracking(false);



    }

    //OWC-653 #vijayrajput 25-12-2015 02-30-pm
    //implement floating action button
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

}
