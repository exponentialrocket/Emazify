package com.emazify;

import android.content.Context;

import com.emazify.inf.EmazyInitializeInf;

/**
 * Created by owc-android on 21/3/18.
 */

public abstract class testClass {

    EmazyInitializeInf emazyInitializeInf=new EmazyInitialize();


    public void callEmazifyLoginApi(final Context context, String custId)
    {
        emazyInitializeInf.callEmazifyLoginApi(context,custId);
    }




}
