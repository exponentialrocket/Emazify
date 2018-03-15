package Utils;

import android.util.Log;

/**
 * Created by owc-android on 13/3/18.
 */

public class utils {

    public static void showErrorLog(String tag, String messageString) {
       /* if (ConfigureVariable.isCrashHandler) {*/
        Log.e(tag, "" + messageString);
      /*  }*/
    }
}
