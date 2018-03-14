package emazify.oneway.com.onewayuser;

import android.content.Context;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import emazify.oneway.com.onewayuser.Utils.ConnectionDetector;
import emazify.oneway.com.onewayuser.Utils.Const;
import emazify.oneway.com.onewayuser.Utils.Pref;
import emazify.oneway.com.onewayuser.Utils.UserFunctions;
import emazify.oneway.com.onewayuser.Utils.utils;

/**
 * Created by owc-android on 13/3/18.
 */

public class onewayuser {
    private ConnectionDetector mConnectionDetector;
    private UserFunctions mUserFunctions;
    private static final String TAG = onewayuser.class.getSimpleName();

    private static onewayuser ourInstance = new onewayuser();

    public static onewayuser getInstance() {
        return ourInstance;
    }

    private onewayuser() {


    }

    public void initialize(Context context) {
        mConnectionDetector = new ConnectionDetector(context);
        mUserFunctions = new UserFunctions(context);
    }

    public void callEmazifyLoginApi(final Context context,String CId) {
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
