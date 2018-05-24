package Utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class LocationTracker implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    // Objects
    private static volatile LocationTracker mLocationTracker = null;
    private final GoogleApiClient mGoogleApiClient;
    private final FusedLocationProviderApi mFusedLocationProviderApi = LocationServices.FusedLocationApi;
    private final Context mContext;
    private final UpdateUI mUpdateUI;
    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;
    // Other variables
    private int INTERVAL = 10000;
    private int FAST_INTERVAL = 5000;

    public LocationTracker(Context context, UpdateUI updateUI, int interval, int fastInterval) {
        mContext = context;
        mUpdateUI = updateUI;
        INTERVAL = interval;
        FAST_INTERVAL = fastInterval;

        createLocationRequest();

        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

    }

    public static LocationTracker newInstance(Context context, UpdateUI updateUI, int interval, int fastInterval) {
        if (mLocationTracker == null) {
            mLocationTracker = new LocationTracker(context, updateUI, interval, fastInterval);
        }
        return mLocationTracker;
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FAST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void connect() {
        mGoogleApiClient.connect();
    }

    public void disConnect() {
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        //mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        if (mUpdateUI != null) {
            mUpdateUI.updateUserInterface(location);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //Utils.showToast(mContext, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }


    public void stopLocationUpdates() {
        if (mGoogleApiClient.isConnected()) {
            mFusedLocationProviderApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }


    public void startLocationUpdates() {
        if (isLocationProviderEnable(mContext)) {
            if (mGoogleApiClient.isConnected()) {
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    //please give location permission
                }
                else {
                    mFusedLocationProviderApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                    mCurrentLocation = mFusedLocationProviderApi.getLastLocation(mGoogleApiClient);
                    if (mCurrentLocation != null && mUpdateUI != null) {
                        mUpdateUI.updateUserInterface(mCurrentLocation);
                    }
                }

            }
        }
    }


    public boolean isLocationProviderEnable(final Context context) {

        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }


        return !(!gps_enabled && !network_enabled) && gps_enabled;
    }

    public interface UpdateUI {
        void updateUserInterface(Location location);
    }
}
