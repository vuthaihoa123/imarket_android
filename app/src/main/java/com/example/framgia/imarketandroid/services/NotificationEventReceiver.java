package com.example.framgia.imarketandroid.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by toannguyen201194 on 18/08/2016.
 */
public class NotificationEventReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentService=new Intent(context,NotificationAlarmService.class);
        context.startService(intentService);
    }
}
