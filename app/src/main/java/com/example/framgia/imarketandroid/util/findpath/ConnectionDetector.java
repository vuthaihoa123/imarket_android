package com.example.framgia.imarketandroid.util.findpath;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

/**
 * Created by TungN10 on 11/19/2015.
 */
public class ConnectionDetector {
    Context context;
    public ConnectionDetector(Context context){
        this.context = context;
    }
    public boolean isConnectToInternet(){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context
            .CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
