package emazify.oneway.com.onewayuser.Utils;

import android.content.Context;

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

public class UserFunctions {

    private static UserFunctions ourInstance = new UserFunctions();

    public static UserFunctions getInstance() {
        return ourInstance;
    }

    private UserFunctions() {
    }

    private static final String TAG = UserFunctions.class.getSimpleName();
    private static final String CONTENT_TYPE = "application/json";

    private static final String EMAZIFY_URL = "https://1ic84mlbk1.execute-api.ap-south-1.amazonaws.com/";
    private static final String EMAZIFY_LOGIN_URL = EMAZIFY_URL+"User_Login_Live";
    private static final String EMAZIFY_USER_PROPERTY_URL = EMAZIFY_URL+"User_Property_Live";

    private static Context mContext = null;
    private static AsyncHttpClient myAsyncHttpClient = null;
    private static AsyncHttpClient myServiceAsyncHttpClient = null;

    public UserFunctions(Context mContext) {
        this.mContext = mContext;
        myAsyncHttpClient = new AsyncHttpClient();
        myAsyncHttpClient.setTimeout(60000);

        myServiceAsyncHttpClient = new SyncHttpClient();
        myServiceAsyncHttpClient.setTimeout(120000);
    }


    public void emazifyLogin(AsyncHttpResponseHandler responseHandler) {


        JSONObject jsonParams;
        try {
            jsonParams = new JSONObject();
            //  jsonParams.put("x-api-key", "EYRXczFacW41SHLP9StgH5EYCFDb9DCa6wvIoZe5");
            jsonParams.put("accountId", "onewaycab");
            jsonParams.put("customerId", Pref.getValue(mContext,Const.CID,""));
            jsonParams.put("emailId", "");
            jsonParams.put("emazyCustomerId", "");
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

    /*public void emazifyLogOut(AsyncHttpResponseHandler responseHandler) {

        JSONObject jsonParams;
        try {
            jsonParams = new JSONObject();
            //  jsonParams.put("x-api-key", "EYRXczFacW41SHLP9StgH5EYCFDb9DCa6wvIoZe5");
            jsonParams.put("accountId", "onewaycab");
            jsonParams.put("customerId", Pref.getValue(mContext,Const.CID,""));
            jsonParams.put("emailId", "");
            jsonParams.put("emazyCustomerId", Pref.getValue(mContext,Const.PREF_EmazyCID,""));
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

    public void emazifyUserProperty(AsyncHttpResponseHandler responseHandler) {

        JSONObject jsonParams;
        try {
            jsonParams = new JSONObject();
            //  jsonParams.put("x-api-key", "EYRXczFacW41SHLP9StgH5EYCFDb9DCa6wvIoZe5");
            jsonParams.put("accountId", "onewaycab");
            jsonParams.put("customerId", Pref.getValue(mContext,Const.CID,""));
            jsonParams.put("customAttributeName", "age");
            jsonParams.put("customAttributeValue", "25");


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

    }*/

    private void showErrorLog(String messageString) {
        utils.showErrorLog(TAG, messageString);
    }
}
