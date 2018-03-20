package Utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * Created by owc-android on 13/3/18.
 */

public class utils implements ActivityCompat.OnRequestPermissionsResultCallback {

    public static String Imei_id = "";
    public static TelephonyManager telephonyManager;

    public static void showErrorLog(String tag, String messageString) {
       /* if (ConfigureVariable.isCrashHandler) {*/
        Log.e(tag, "" + messageString);
      /*  }*/
    }

    public static String getVersion(Context context) {

        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        String version = "";
        try {
            info = manager.getPackageInfo(
                    context.getPackageName(), 0);
            version = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        showErrorLog("App Version", version);
        return version;

    }

    public static String getIMEINumber(Context context, Activity activity) {


        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] PERMISSIONS = {Manifest.permission.READ_PHONE_STATE};

            if (!hasPermissions(context, PERMISSIONS)) {
                if (!Settings.System.canWrite(context)) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, Const.READ_PHONE_CODE);
                }
            } else {
                // continue with your code
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    Imei_id = telephonyManager.getDeviceId();
                }

            }
        } else {
            // continue with your code
            Imei_id = telephonyManager.getDeviceId();
        }

        showErrorLog("Imei_id ID",Imei_id);
        return Imei_id;
    }

    public static String getAndroidOsName(){
        StringBuilder builder = new StringBuilder();
        builder.append("android : ").append(Build.VERSION.RELEASE);

        Field[] fields = Build.VERSION_CODES.class.getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            int fieldValue = -1;

            try {
                fieldValue = field.getInt(new Object());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            if (fieldValue == Build.VERSION.SDK_INT) {
                builder.append(" : ").append(fieldName).append(" : ");
                builder.append("sdk=").append(fieldValue);
            }
        }
        showErrorLog("OS Name",builder.toString());

        return builder.toString();
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Const.READ_PHONE_CODE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Imei_id = telephonyManager.getDeviceId();
                } else {

                }

                return;
            }
        }
    }

    public static String getDeviceId(Context context) {

        String android_id = "";
        android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        showErrorLog("Device ID",android_id);
        return android_id;
    }

    public static String getAndroidManufacturer() {
        String manufacturer = "";

        try{
            manufacturer = Build.MANUFACTURER;
        }catch (NullPointerException e){

        }catch (Exception e){

        }

        showErrorLog("Manufacturer",manufacturer);
        return manufacturer;
    }

    public static String getAndroidOSVersion() {
        String version = "";

        try{
            version = String.valueOf(Build.VERSION.BASE_OS);

        }catch (NullPointerException e){

        }catch (Exception e){

        }

        showErrorLog("OS Version",version);
        return version;
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }



        showErrorLog("Device Name",phrase.toString());
        return phrase.toString();
    }


    public static String getSDKVersion(Context context){
        String version = "";
        Properties properties = new Properties();

        try {
            InputStream inputStream =
                    context.getClass().getClassLoader().getResourceAsStream("fileName.properties");
            properties.load(inputStream);
            version=properties.getProperty("version");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return version;
    }
}
