package com.emazify.Services;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;

import java.util.List;
import java.util.Locale;

import Utils.Const;
import Utils.LocationTracker;
import Utils.Pref;
import Utils.utils;

public class FetchUserCityService extends IntentService implements LocationTracker.UpdateUI {
    private static final String TAG = FetchUserCityService.class.getName();
    private LocationTracker mLocationTracker;
    private String accountId;

    public FetchUserCityService() {
        super("FetchUserCityService");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mLocationTracker = LocationTracker.newInstance(FetchUserCityService.this, this, 10000, 10000);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mLocationTracker != null) {
            mLocationTracker.connect();
        }
        accountId=(String) intent.getExtras().get("accountId");

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (!mLocationTracker.isLocationProviderEnable(getBaseContext())) {
            //fix issue of get user location and display route list as per location
            Pref.setValue(getApplicationContext(), Const.PREF_USER_LAT, "0");
            Pref.setValue(getApplicationContext(), Const.PREF_USER_LONG, "0");
            Pref.setValue(getApplicationContext(), Const.PREF_USER_CITY, "");
        }
        else {
            if (mLocationTracker != null) {
                mLocationTracker.startLocationUpdates();
            }
        }
    }

    @Override
    public boolean stopService(Intent name) {
        if (mLocationTracker != null) {
            mLocationTracker.disConnect();
        }
        return super.stopService(name);
    }


    @Override
    public void updateUserInterface(Location location) {
        Pref.setValue(getApplicationContext(), Const.PREF_USER_LAT, "" + location.getLatitude());
        Pref.setValue(getApplicationContext(), Const.PREF_USER_LONG, "" + location.getLongitude());
        new FetchUserCity().execute();
        mLocationTracker.stopLocationUpdates();
        startService(new Intent(getApplicationContext(), AppDetectservice.class));

        Intent appDetectService = new Intent(getApplicationContext(), AppDetectservice.class);
        appDetectService.putExtra("accountId",accountId);
        startService(appDetectService);

    }

    private void showErrorLog(String messageString) {
        utils.showErrorLog(TAG, messageString);
    }

    private class FetchUserCity extends AsyncTask<String, Integer, String> {
        private String city = "";


        @Override
        protected String doInBackground(String... url) {
            try {
                String userLat = Pref.getValue(getApplicationContext(), Const.PREF_USER_LAT, "0");
                String userLong = Pref.getValue(getApplicationContext(), Const.PREF_USER_LONG, "0");
                showErrorLog("userLat==>" + userLat);
                showErrorLog("userLong==>" + userLong);
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                addresses = geocoder.getFromLocation(Double.parseDouble(userLat), Double.parseDouble(userLong), 1);
                city = addresses.get(0).getLocality();
                Pref.setValue(getApplicationContext(), Const.PREF_USER_CITY, "" + city);
            }
            catch (Exception e) {
                e.printStackTrace();
                showErrorLog(e.toString());
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            Pref.setValue(getApplicationContext(), Const.PREF_USER_CITY, "" + city);
        }
    }

}
