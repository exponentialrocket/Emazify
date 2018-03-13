package emazify.oneway.com.onewayuser.Utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class Pref {
    private static volatile SharedPreferences sharedPreferences = null;

    private Pref() {
    }

    private static void openPref(Context context) {
        sharedPreferences = context.getSharedPreferences(Const.PREF_FILE, Context.MODE_PRIVATE);
    }

    public static String getValue(Context context, String key, String defaultValue) {
        if (null != context) {
            Pref.openPref(context);
            String result = Pref.sharedPreferences.getString(key, defaultValue);
            Pref.sharedPreferences = null;
            return result;
        }
        return "";
    }

    public static void setValue(Context context, String key, int value) {
        if (null != context) {
            Pref.openPref(context);
            Editor prefsPrivateEditor = Pref.sharedPreferences.edit();
            prefsPrivateEditor.putInt(key, value);
            prefsPrivateEditor.apply();
            Pref.sharedPreferences = null;
        }
    }

    public static int getValue(Context context, String key, int defaultValue) {
        if (null != context) {
            Pref.openPref(context);
            int result = Pref.sharedPreferences.getInt(key, defaultValue);
            Pref.sharedPreferences = null;
            return result;
        }
        return 0;
    }

    public static void setValue(Context context, String key, String value) {
        if (null != context) {
            Pref.openPref(context);
            Editor prefsPrivateEditor = Pref.sharedPreferences.edit();
            prefsPrivateEditor.putString(key, value);
            prefsPrivateEditor.apply();
            Pref.sharedPreferences = null;
        }
    }

    public static boolean getValue(Context context, String key, boolean defaultValue) {
        if (null != context) {
            Pref.openPref(context);
            boolean result = Pref.sharedPreferences.getBoolean(key, defaultValue);
            Pref.sharedPreferences = null;
            return result;
        }
        return false;
    }

    public static void setValue(Context context, String key, boolean value) {
        if (null != context) {
            Pref.openPref(context);
            Editor prefsPrivateEditor = Pref.sharedPreferences.edit();
            prefsPrivateEditor.putBoolean(key, value);
            prefsPrivateEditor.apply();
            Pref.sharedPreferences = null;
        }
    }
}
