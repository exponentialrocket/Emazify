package receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.emazify.EmazyInitialize;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import Utils.ConnectionDetector;
import Utils.Const;
import Utils.Pref;
import Utils.UserFunctions;
import Utils.utils;

public class MyBroadcastReceiver extends BroadcastReceiver {

    private ConnectionDetector mConnectionDetector;
    private UserFunctions mUserFunctions;

    @Override
    public void onReceive(Context context, Intent intent) {

        String campaignUniqId = intent.getStringExtra("campaignDataUniqueId");

        if(intent.hasExtra("notiClear")){
            EmazifyNotiUpdate(context,campaignUniqId,"1");
        }else if(intent.hasExtra("notiClick")){
            EmazifyNotiUpdate(context,campaignUniqId,"2");
        }
    }

    public void EmazifyNotiUpdate(final Context context, String campaignUniqueId,String notiEvent) {
        mConnectionDetector = new ConnectionDetector(context);
        mUserFunctions = new UserFunctions(context);
        mUserFunctions.emazifyNotiUpdate(campaignUniqueId,notiEvent,new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    //OWC-2517 #prashantjajal 18-04-2016 011-10-am
                    //implement double click for disable button
                    if (response != null) {
                        showErrorLog("emazifyNotiUpdate Result==>" + response.toString());

                    }
                    else {


                    }

                }
                catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

    }

    private void showErrorLog(String messageString) {
        utils.showErrorLog("MyBroadcast", messageString);
    }
}

