package com.example.framgia.imarketandroid.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.framgia.imarketandroid.services.NotificationEventReceiver;

import java.util.Calendar;

/**
 * Created by toannguyen201194 on 19/08/2016.
 */
public class NotificationUtil {
    public static void setAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, NotificationEventReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
        Calendar alarmStartTime = Calendar.getInstance();
        alarmStartTime.set(Calendar.HOUR_OF_DAY, 8);
        alarmStartTime.set(Calendar.MINUTE, 0);
        alarmStartTime.set(Calendar.SECOND, 0);
        alarmStartTime.set(Calendar.AM_PM,Calendar.AM);
        alarmManager.setRepeating(AlarmManager.RTC, alarmStartTime.getTimeInMillis(), getInterval
            (), pendingIntent);
    }

    private static int getInterval() {
        int day=1;
        int hours=24;
        int minutes=60;
        int seconds = 60;
        int milliseconds = 1000;
        int repeatMS = day*hours*minutes*seconds * milliseconds;
        return repeatMS;
    }
}
