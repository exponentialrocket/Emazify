package gcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.emazify.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Map;

import Utils.Const;

public class MyFcmListenerService extends FirebaseMessagingService {

    private static final String TAG = MyFcmListenerService.class.getSimpleName();
    private int loginType = 0;
    private int userLoginType = 0;
    String message;
    String strNewBooking;
    String currentDriverId = "";
    String currentDisplayDriverId = "";
    int bookingId;
    AlertDialog alertDialog;

    @Override
    public void onMessageReceived(RemoteMessage msg) {

        Map<String, String> receivedMap = msg.getData();

        message = receivedMap.get(Const.MESSAGE);
        Log.e("", "MyFcmListenerService MSG : "+message);

        JSONObject jsonObject = null;

        Log.e("","onMessageReceived FCM Message: " + message);


                sendNotification(message);

             Log.e("", "Map receivedMap: " + receivedMap);


    }


    private void sendNotification(String message) {
/*

        Intent intent = new Intent(this, .class);

        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
*/


        //OWC-960 #vijayrajput 03-12-2015 02-00-pm
        //fix issue on multiple notification click and ride detail not load properly
        int requestID = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestID /* Request code */, new Intent() , PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle().bigText(message);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.back_white_icon)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(message)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(this, android.R.color.transparent))
                .setStyle(bigTextStyle)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        notificationBuilder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(requestID/* ID of notification */, notificationBuilder.build());

    }

  /*  private void showErrorLog(String messageString) {
        Utils.showErrorLog(TAG, messageString);
    }*/


}