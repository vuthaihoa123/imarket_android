package com.example.framgia.imarketandroid.util.findpath;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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
        if (!result)
            new AlertDialog.Builder(context)
                .setTitle(R.string.infor)
                .setMessage(R.string.not_internet)
                .setPositiveButton(context.getResources().getString(R.string.ok_dialog_success),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        return result;
    }
}
