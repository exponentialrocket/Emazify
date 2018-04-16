package Utils;

import android.content.Context;
import android.location.LocationManager;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BuildConfig;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static Utils.Const.*;

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
    private static String EMAZIFY_LOGIN_URL = "";
    private static String EMAZIFY_USER_PROPERTY_URL = "";
    private static String EMAZIFY_USER_AUTO_PROPERTY_URL = "";
    private static String EMAZIFY_EVENTS = "";

    private static String EMAZIFY_LOGIN_KEY = "";
    private static String EMAZIFY_USER_PROPERTY_KEY = "";
    private static String EMAZIFY_USER_AUTO_PROPERTY_KEY = "";
    private static String EMAZIFY_EVENTS_KEY = "";

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
            emaziCustId = Pref.getValue(mContext, PREF_EmazyCID,"");

        }catch (NullPointerException e){

        }catch (Exception e){

        }

        if(!isLive){
            EMAZIFY_LOGIN_URL = "https://qivs929o3a.execute-api.ap-south-1.amazonaws.com/user_login_api";
            EMAZIFY_USER_PROPERTY_URL = "https://immrpob4v7.execute-api.ap-south-1.amazonaws.com/user_property";
            EMAZIFY_USER_AUTO_PROPERTY_URL = "https://py3r76iv4j.execute-api.ap-south-1.amazonaws.com/user_auto_system_property";
            EMAZIFY_EVENTS = "https://7boo62w8mg.execute-api.ap-south-1.amazonaws.com/user_custom_event";

            EMAZIFY_LOGIN_KEY = "3EfcZaDxBO3WN8HiRauwv9KmQrEQSaU67yK4Bloh";
            EMAZIFY_USER_PROPERTY_KEY = "R6V5S8435O7UfN6Cl4vQb1pcc5y9V2YJ4SziRsQr";
            EMAZIFY_USER_AUTO_PROPERTY_KEY = "w8KYpQLT0o3Wmc41qFMqcmFlsHLmrz4CvdfEps10";
            EMAZIFY_EVENTS_KEY = "nUcXZCYFut8W5dGYB8yge8RJtNPgb7guFVg7r200";

        }else{
            EMAZIFY_LOGIN_URL = "https://m0vd9ivjpj.execute-api.ap-south-1.amazonaws.com/user_login_api_live";
            EMAZIFY_USER_PROPERTY_URL = "https://fkhmt0jdjj.execute-api.ap-south-1.amazonaws.com/user_property_live";
            EMAZIFY_USER_AUTO_PROPERTY_URL = "https://jdypv37xbl.execute-api.ap-south-1.amazonaws.com/user_auto_system_property_live";
            EMAZIFY_EVENTS = "https://jyu38ju154.execute-api.ap-south-1.amazonaws.com/user_custom_event_live";

            EMAZIFY_LOGIN_KEY = "I9zNQdZPf96poDtXCRmNW8UeDU9qW0j79wu76Cig";
            EMAZIFY_USER_PROPERTY_KEY = "296kOXrkjv3IPyvOYzKf34Tt04IauYTY7zVlfXjm";
            EMAZIFY_USER_AUTO_PROPERTY_KEY = "rA5dTpW9xQ6wcGAfBRFjZN9l37Ptyk941ZTiLo26";
            EMAZIFY_EVENTS_KEY = "5dyLY8WlFReBpkk2mrpt12caNum8I2S18JKzkYp0";

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

            myAsyncHttpClient.addHeader("x-api-key", EMAZIFY_LOGIN_KEY);
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

            showErrorLog("emazify logout Params " + jsonParams.toString());

            StringEntity entity = new StringEntity(jsonParams.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE));
            myAsyncHttpClient.addHeader("x-api-key", EMAZIFY_LOGIN_KEY);
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
            jsonParams.put("emazyCustomerId", emaziCustId);
            jsonParams.put("customAttributeName", customAttributeName);
            jsonParams.put("customAttributeValue", customAttributeValue);

            showErrorLog("emazify user property Url " + EMAZIFY_USER_PROPERTY_URL);
            showErrorLog("emazify user property Params " + jsonParams.toString());

            StringEntity entity = new StringEntity(jsonParams.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE));
            myAsyncHttpClient.addHeader("x-api-key", EMAZIFY_USER_PROPERTY_KEY);
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
            jsonParams.put("emazyCustomerId", emaziCustId);
            jsonParams.put("customAttributeName", customAttributeName);
            jsonParams.put("customAttributeValue", customAttributeValue);

            showErrorLog("emazify user property Url " + EMAZIFY_USER_PROPERTY_URL);
            showErrorLog("emazify user property Params " + jsonParams.toString());

            StringEntity entity = new StringEntity(jsonParams.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE));
            myAsyncHttpClient.addHeader("x-api-key", EMAZIFY_USER_PROPERTY_KEY);
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
            jsonParams.put("emazyCustomerId", emaziCustId);
            jsonParams.put("customAttributeName", customAttributeName);
            jsonParams.put("customAttributeValue", customAttributeValue);

            showErrorLog("emazify user property Url " + EMAZIFY_USER_PROPERTY_URL);
            showErrorLog("emazify user property Params " + jsonParams.toString());

            StringEntity entity = new StringEntity(jsonParams.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE));
            myAsyncHttpClient.addHeader("x-api-key", EMAZIFY_USER_PROPERTY_KEY);
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
            jsonParams.put("emazyCustomerId", emaziCustId);
            jsonParams.put("customAttributeName", customAttributeName);
            jsonParams.put("customAttributeValue", customAttributeValue);

            showErrorLog("emazify user property Url " + EMAZIFY_USER_PROPERTY_URL);
            showErrorLog("emazify user property Params " + jsonParams.toString());

            StringEntity entity = new StringEntity(jsonParams.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE));
            myAsyncHttpClient.addHeader("x-api-key", EMAZIFY_USER_PROPERTY_KEY);
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
            jsonParams.put("emazyCustomerId", emaziCustId);
            jsonParams.put("customAttributeName", customAttributeName);
            jsonParams.put("customAttributeValue", customAttributeValue);

            showErrorLog("emazify user property Url " + EMAZIFY_USER_PROPERTY_URL);
            showErrorLog("emazify user property Params " + jsonParams.toString());

            StringEntity entity = new StringEntity(String.valueOf(jsonParams));
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE));
            myAsyncHttpClient.addHeader("x-api-key", EMAZIFY_USER_PROPERTY_KEY);
            myAsyncHttpClient.post(mContext, EMAZIFY_USER_PROPERTY_URL , entity, CONTENT_TYPE, responseHandler);
        }
        catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public void emazifyEvents(String CustId, String eventName, Map<String,Object> properties, AsyncHttpResponseHandler responseHandler) {

        Map<String,Object> map=new HashMap<String,Object>();
        map.put("accountId", "onewaycab");
        map.put("customerId", CustId);
        map.put("emazyCustomerId", emaziCustId);
        map.put("eventName", eventName);
        map.put("properties",properties);

        Gson locationGson  = new Gson();
        String loc= locationGson.toJson(map);

        showErrorLog("EMAZIFY_EVENTS Url " + EMAZIFY_EVENTS);
        showErrorLog("EMAZIFY_EVENTS Params " + String.valueOf(loc.toString()));

        StringEntity entity = null;
        try {
            entity = new StringEntity(loc);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE));
        myAsyncHttpClient.addHeader("x-api-key", EMAZIFY_EVENTS_KEY);
        myAsyncHttpClient.post(mContext, EMAZIFY_EVENTS , entity, CONTENT_TYPE, responseHandler);
    }

    public void emazifyAutoSystemUserProperty(String CustId,String mobNo,String email,
                                              String fcmToken,String ezPushNotiEnabled,String latlng,
                                              AsyncHttpResponseHandler responseHandler) {

        JSONObject jsonParams;
        try {

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
            jsonParams.put("ezSdkVersion", SDK_Version);
            jsonParams.put("ezUserAutoLocation", latlng);
            jsonParams.put("source", "Android");

            showErrorLog("emazifyAutoSystemUserProperty Url " + EMAZIFY_USER_AUTO_PROPERTY_URL);
            showErrorLog("emazifyAutoSystemUserProperty Params " + jsonParams.toString());

            StringEntity entity = new StringEntity(jsonParams.toString());
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE));
            myAsyncHttpClient.addHeader("x-api-key", EMAZIFY_USER_AUTO_PROPERTY_KEY);
            myAsyncHttpClient.post(mContext, EMAZIFY_USER_AUTO_PROPERTY_URL, entity, CONTENT_TYPE, responseHandler);
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    private void showErrorLog(String messageString) {
        utils.showErrorLog(TAG, messageString);
    }




}
