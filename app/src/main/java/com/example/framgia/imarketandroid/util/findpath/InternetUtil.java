package com.example.framgia.imarketandroid.util.findpath;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.framgia.imarketandroid.R;

/**
 * Created by phongtran on 23/09/2016.
 */
public class InternetUtil {
    public static boolean isInternetConnected(Context context) {
        ConnectivityManager connectivityManager =
            (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean result = false;
        if (connectivityManager != null) {
            NetworkInfo networkInfo =
                connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null
                && (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE
                || networkInfo.getType() == ConnectivityManager.TYPE_WIFI)) {
                result = true;
            }
        }
//        if (!result)
//            Toast.makeText(context, R.string.no_internet, Toast.LENGTH_SHORT).show();
        return result;
    }
}
