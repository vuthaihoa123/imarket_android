package com.example.framgia.imarketandroid.util;

import android.util.Log;

/**
 * Created by hoavt on 21/07/2016.
 */
public class Flog {
    private static final String TAG = "IMarKet";
    private static boolean mShow = true;

    public static void i(String content) {
        if (mShow) {
            Log.i(TAG, content + "");
        }
    }
}
