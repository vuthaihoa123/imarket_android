package com.example.framgia.imarketandroid.util.findpath;

import com.example.framgia.imarketandroid.util.Constants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by tranphong on 16/12/2016.
 */
public class DateTimeUtil {
    public static String getTimeEvent(String startTime, String endTime) {
        String time = Constants.TITLE_TIME;
        startTime = startTime.substring(0, startTime.indexOf(Constants.KEY_T));
        try {
            DateFormat formatter = new SimpleDateFormat(Constants.DateTime.FORMAT_DATE,
                Locale.getDefault());
            Date date = null;
            date = formatter.parse(startTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            time += calendar.get(Calendar.DAY_OF_MONTH) + (calendar.get(Calendar.MONTH) + 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        time += Constants.PATTERN_SEPARATION;
        endTime = endTime.substring(0, endTime.indexOf(Constants.KEY_T));
        try {
            DateFormat formatter = new SimpleDateFormat(Constants.DateTime.FORMAT_DATE,
                Locale.getDefault());
            Date date = null;
            date = formatter.parse(endTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            time += calendar.get(Calendar.DAY_OF_MONTH) +
                Constants.SEPARATOR + (calendar.get(Calendar.MONTH) + 1)
                + Constants.SEPARATOR + calendar.get(Calendar.YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
}
