package com.example.framgia.imarketandroid.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.framgia.imarketandroid.services.NotificationAlarmService;

import java.util.Calendar;

/**
 * Created by toannguyen201194 on 19/08/2016.
 */
public class NotificationUtil {
    public static void setAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, NotificationAlarmService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar alarmStartTime = Calendar.getInstance();
        Calendar currentCal = Calendar.getInstance();
        alarmStartTime.set(Calendar.HOUR_OF_DAY, Constants.ALARM_START_HOUR);
        alarmStartTime.set(Calendar.MINUTE, Constants.ALARM_START_MINUTES);
        alarmStartTime.set(Calendar.SECOND, Constants.ALARM_START_SECONDS);
        alarmStartTime.set(Calendar.AM_PM, Calendar.AM);
        long intendedTime = alarmStartTime.getTimeInMillis();
        long currentTime = currentCal.getTimeInMillis();
        if (intendedTime >= currentTime) {
            alarmManager
                .setInexactRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(),
                    alarmStartTime.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                intendedTime, getInterval(), pendingIntent);
        }
    }

    public static int getInterval() {
        int repeat;
        int hours = 4;
        int minutes = 60;
        int seconds = 60;
        int miliseconds = 1000;
        return repeat = hours * minutes * seconds * miliseconds;
    }
}
