package Utils;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by owc-android on 13/3/18.
 */

public class UserFunctions{

    private LocationManager locationManager;

    private static UserFunctions ourInstance = new UserFunctions();

    public static UserFunctions getInstance() {
        return ourInstance;
    }

    private UserFunctions() {
    }

    private static final String TAG = UserFunctions.class.getSimpleName();
    private static final String CONTENT_TYPE = "application/json";

    private static final String EMAZIFY_LOGIN_URL = "https://1ic84mlbk1.execute-api.ap-south-1.amazonaws.com/" + "User_Login_Live";
    private static final String EMAZIFY_USER_PROPERTY_URL = "https://hcbt0pcsd0.execute-api.ap-south-1.amazonaws.com/" + "User_Property_Live";
    private static final String EMAZIFY_USER_AUTO_PROPERTY_URL = "https://nl5zif7r9d.execute-api.ap-south-1.amazonaws.com/user_auto_system_property_live";

    private static String emaziCustId = "";

    private static Context mContext = null;
    private static AsyncHttpClient myAsyncHttpClient = null;
    private static AsyncHttpClient myServiceAsyncHttpClient = null;

    public UserFunctions(Context mContext) {
        this.mContext = mContext;
        myAsyncHttpClient = new AsyncHttpClient();
        myAsyncHttpClient.setTimeout(60000);

        myServiceAsyncHttpClient = new SyncHttpClient();
        myServiceAsyncHttpClient.setTimeout(120000);

        try{
            emaziCustId = Pref.getValue(mContext,Const.PREF_EmazyCID,"");

        }catch (NullPointerException e){

        }catch (Exception e){

        }
    }


    public void emazifyLogin(String CustId,AsyncHttpResponseHandler responseHandler) {


        JSONObject jsonParams;
        try {



            jsonParams = new JSONObject();
            jsonParams.put("accountId", "onewaycab");
            jsonParams.put("customerId", CustId);
            jsonParams.put("emailId", "");
            jsonParams.put("emazyCustomerId", emaziCustId);
            jsonParams.put("mobileNumber", "9408564247");
            jsonParams.put("eventName", "login");


            //    showErrorLog("emazify Url " + EMAZIFY_LOGIN_URL);
            showErrorLog("emazify Params " + jsonParams.toString());

            StringEntity entity = new StringEntity(jsonParams.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE));
            myAsyncHttpClient.addHeader("x-api-key", "EYRXczFacW41SHLP9StgH5EYCFDb9DCa6wvIoZe5");
            myAsyncHttpClient.post(mContext, EMAZIFY_LOGIN_URL , entity, CONTENT_TYPE, responseHandler);
        }
        catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }




    public void emazifyLogOut(String CustId,AsyncHttpResponseHandler responseHandler) {

        JSONObject jsonParams;
        try {


            jsonParams = new JSONObject();
            //  jsonParams.put("x-api-key", "EYRXczFacW41SHLP9StgH5EYCFDb9DCa6wvIoZe5");
            jsonParams.put("accountId", "onewaycab");
            jsonParams.put("customerId", CustId);
            jsonParams.put("emailId", "");
            jsonParams.put("emazyCustomerId", emaziCustId);
            jsonParams.put("mobileNumber", "9408564247");
            jsonParams.put("eventName", "logout");

            //    showErrorLog("emazify Url " + EMAZIFY_LOGIN_URL);
            showErrorLog("emazify logout Params " + jsonParams.toString());

            StringEntity entity = new StringEntity(jsonParams.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE));
            myAsyncHttpClient.addHeader("x-api-key", "EYRXczFacW41SHLP9StgH5EYCFDb9DCa6wvIoZe5");
            myAsyncHttpClient.post(mContext, EMAZIFY_LOGIN_URL , entity, CONTENT_TYPE, responseHandler);
        }
        catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public void emazifyUserProperty(String CustId,String customAttributeName,String customAttributeValue,AsyncHttpResponseHandler responseHandler) {

        JSONObject jsonParams;
        try {
            jsonParams = new JSONObject();
            //  jsonParams.put("x-api-key", "EYRXczFacW41SHLP9StgH5EYCFDb9DCa6wvIoZe5");
            jsonParams.put("accountId", "onewaycab");
            jsonParams.put("customerId", CustId);
            jsonParams.put("customAttributeName", customAttributeName);
            jsonParams.put("customAttributeValue", customAttributeValue);

            showErrorLog("emazify user property Url " + EMAZIFY_USER_PROPERTY_URL);
            showErrorLog("emazify user property Params " + jsonParams.toString());

            StringEntity entity = new StringEntity(jsonParams.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE));
            myAsyncHttpClient.addHeader("x-api-key", "528VEvkvuUlrjPpcfQfB6uMzxHb1IQg6boqconG0");
            myAsyncHttpClient.post(mContext, EMAZIFY_USER_PROPERTY_URL , entity, CONTENT_TYPE, responseHandler);
        }
        catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public void emazifyUserProperty(String CustId,String customAttributeName,float customAttributeValue,AsyncHttpResponseHandler responseHandler) {

        JSONObject jsonParams;
        try {
            jsonParams = new JSONObject();
            //  jsonParams.put("x-api-key", "EYRXczFacW41SHLP9StgH5EYCFDb9DCa6wvIoZe5");
            jsonParams.put("accountId", "onewaycab");
            jsonParams.put("customerId", CustId);
            jsonParams.put("customAttributeName", customAttributeName);
            jsonParams.put("customAttributeValue", customAttributeValue);

            showErrorLog("emazify user property Url " + EMAZIFY_USER_PROPERTY_URL);
            showErrorLog("emazify user property Params " + jsonParams.toString());

            StringEntity entity = new StringEntity(jsonParams.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE));
            myAsyncHttpClient.addHeader("x-api-key", "528VEvkvuUlrjPpcfQfB6uMzxHb1IQg6boqconG0");
            myAsyncHttpClient.post(mContext, EMAZIFY_USER_PROPERTY_URL , entity, CONTENT_TYPE, responseHandler);
        }
        catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public void emazifyUserProperty(String CustId,String customAttributeName,int customAttributeValue,AsyncHttpResponseHandler responseHandler) {

        JSONObject jsonParams;
        try {
            jsonParams = new JSONObject();
            //  jsonParams.put("x-api-key", "EYRXczFacW41SHLP9StgH5EYCFDb9DCa6wvIoZe5");
            jsonParams.put("accountId", "onewaycab");
            jsonParams.put("customerId", CustId);
            jsonParams.put("customAttributeName", customAttributeName);
            jsonParams.put("customAttributeValue", customAttributeValue);

            showErrorLog("emazify user property Url " + EMAZIFY_USER_PROPERTY_URL);
            showErrorLog("emazify user property Params " + jsonParams.toString());

            StringEntity entity = new StringEntity(jsonParams.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE));
            myAsyncHttpClient.addHeader("x-api-key", "528VEvkvuUlrjPpcfQfB6uMzxHb1IQg6boqconG0");
            myAsyncHttpClient.post(mContext, EMAZIFY_USER_PROPERTY_URL , entity, CONTENT_TYPE, responseHandler);
        }
        catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }


    public void emazifyUserProperty(String CustId,String customAttributeName,double customAttributeValue,AsyncHttpResponseHandler responseHandler) {

        JSONObject jsonParams;
        try {
            jsonParams = new JSONObject();
            //  jsonParams.put("x-api-key", "EYRXczFacW41SHLP9StgH5EYCFDb9DCa6wvIoZe5");
            jsonParams.put("accountId", "onewaycab");
            jsonParams.put("customerId", CustId);
            jsonParams.put("customAttributeName", customAttributeName);
            jsonParams.put("customAttributeValue", customAttributeValue);

            showErrorLog("emazify user property Url " + EMAZIFY_USER_PROPERTY_URL);
            showErrorLog("emazify user property Params " + jsonParams.toString());

            StringEntity entity = new StringEntity(jsonParams.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE));
            myAsyncHttpClient.addHeader("x-api-key", "528VEvkvuUlrjPpcfQfB6uMzxHb1IQg6boqconG0");
            myAsyncHttpClient.post(mContext, EMAZIFY_USER_PROPERTY_URL , entity, CONTENT_TYPE, responseHandler);
        }
        catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public void emazifyUserProperty(String CustId,String customAttributeName,long customAttributeValue,AsyncHttpResponseHandler responseHandler) {

        JSONObject jsonParams;
        try {
            jsonParams = new JSONObject();
            //  jsonParams.put("x-api-key", "EYRXczFacW41SHLP9StgH5EYCFDb9DCa6wvIoZe5");
            jsonParams.put("accountId", "onewaycab");
            jsonParams.put("customerId", CustId);
            jsonParams.put("customAttributeName", customAttributeName);
            jsonParams.put("customAttributeValue", customAttributeValue);

            showErrorLog("emazify user property Url " + EMAZIFY_USER_PROPERTY_URL);
            showErrorLog("emazify user property Params " + jsonParams.toString());

            StringEntity entity = new StringEntity(jsonParams.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE));
            myAsyncHttpClient.addHeader("x-api-key", "528VEvkvuUlrjPpcfQfB6uMzxHb1IQg6boqconG0");
            myAsyncHttpClient.post(mContext, EMAZIFY_USER_PROPERTY_URL , entity, CONTENT_TYPE, responseHandler);
        }
        catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public void emazifyAutoSystemUserProperty(String CustId,String mobNo,String email,
                                              String fcmToken,String ezPushNotiEnabled,String latlng,
                                              AsyncHttpResponseHandler responseHandler) {

        JSONObject jsonParams;
        try {

          /*  if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.*/
           // }


            // Initialize the location fields


            jsonParams = new JSONObject();
            //  jsonParams.put("x-api-key", "EYRXczFacW41SHLP9StgH5EYCFDb9DCa6wvIoZe5");
            jsonParams.put("accountId", "onewaycab");
            jsonParams.put("customerId", CustId);
            jsonParams.put("mobileNumber", mobNo);
            jsonParams.put("ezUserPlatform", "Android");
            jsonParams.put("emazyCustomerId", emaziCustId);
            jsonParams.put("emailId", email);
            jsonParams.put("imei", utils.getDeviceIMEI(mContext));
            jsonParams.put("osName", utils.getAndroidOsName());
            jsonParams.put("ezAndroidFcmRegToken", fcmToken);
            jsonParams.put("ezPushNotificationEnabled", ezPushNotiEnabled);
            jsonParams.put("userAppVersion", utils.getVersion(mContext));
            jsonParams.put("ezUserAndroidModel", utils.getDeviceName());
            jsonParams.put("androidDeviceId", utils.getDeviceId(mContext));
            jsonParams.put("ezUserAndroidManufacturer", utils.getAndroidManufacturer());
            jsonParams.put("androidVersion", utils.getOsVersion());
            jsonParams.put("ezSdkVersion", Const.SDK_Version);
            jsonParams.put("ezUserAutoLocation", latlng);
            jsonParams.put("source", "Android");

            showErrorLog("emazifyAutoSystemUserProperty Url " + EMAZIFY_USER_AUTO_PROPERTY_URL);
            showErrorLog("emazifyAutoSystemUserProperty Params " + jsonParams.toString());

            StringEntity entity = new StringEntity(jsonParams.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE));
            myAsyncHttpClient.addHeader("x-api-key", "szglAeIYnO6UW2wKavGiU7FO0XCQHViGMKbyUtPh");
            myAsyncHttpClient.post(mContext, EMAZIFY_USER_AUTO_PROPERTY_URL, entity, CONTENT_TYPE, responseHandler);
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    private void showErrorLog(String messageString) {
        utils.showErrorLog(TAG, messageString);
    }




}
