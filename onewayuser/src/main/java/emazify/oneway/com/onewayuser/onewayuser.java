package emazify.oneway.com.onewayuser;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by owc-android on 13/3/18.
 */

public class onewayuser {

    private static onewayuser ourInstance = new onewayuser();

    public static onewayuser getInstance() {
        return ourInstance;
    }

    private onewayuser() {
    }

    public void makeMeAwesome(Context context, String data) {
        Toast.makeText(context, "Awesome " + data, Toast.LENGTH_LONG).show();
    }
}
