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

public class utils{

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

    @SuppressLint("MissingPermission")
    public static String getDeviceIMEI(Context context) {
        String deviceInternationalMobileEquipmentIdentity = null;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) {
                deviceInternationalMobileEquipmentIdentity = tm.getDeviceId();
            }
            if (deviceInternationalMobileEquipmentIdentity == null || deviceInternationalMobileEquipmentIdentity.length() == 0) {
                //OWC-768 #vijayrajput 23-12-2015 11-30-am
                //read phone permission not given or device International Mobile Equipment Identity not found
                //get android id and pass to device International Mobile Equipment Identity
                deviceInternationalMobileEquipmentIdentity = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        }
        catch (Exception e) {
            //OWC-768 #vijayrajput 23-12-2015 11-30-am
            //read phone permission not given or device International Mobile Equipment Identity not found
            //get android id and pass to device International Mobile Equipment Identity
            deviceInternationalMobileEquipmentIdentity = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            e.printStackTrace();

        }
        return deviceInternationalMobileEquipmentIdentity;
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

    public static String getOsVersion() {
        String osVersion;
        try {
            osVersion = Build.VERSION.RELEASE;
        }
        catch (Exception e) {
            osVersion = "no_os_version";
            e.printStackTrace();
        }
        return osVersion;
    }

    public static String getDeviceName() {
        String deviceName;
        try {
            deviceName = Build.MANUFACTURER + " " + Build.MODEL;
        }
        catch (Exception e) {
            deviceName = "no_device_name";
            e.printStackTrace();
        }
        return deviceName;
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
