package Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by owc-android on 13/3/18.
 */

public class utils {

    public static void showErrorLog(String tag, String messageString) {
       /* if (ConfigureVariable.isCrashHandler) {*/
        Log.e(tag, "" + messageString);
      /*  }*/
    }

    public static String getVersion(Context context) {

        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        String version ="";
        try {
            info = manager.getPackageInfo(
                    context.getPackageName(), 0);
            version = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        showErrorLog("App Version",version);
        return version;

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
