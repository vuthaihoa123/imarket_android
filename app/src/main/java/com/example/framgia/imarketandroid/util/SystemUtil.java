package com.example.framgia.imarketandroid.util;

import android.content.Context;

import com.example.framgia.imarketandroid.R;

import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

/**
 * Created by hoavt on 18/08/2016.
 */
public class SystemUtil {
    public static String formatMoneyStr(long money) {
        DecimalFormat dec = new DecimalFormat(Constants.FORMAT_MONEY);
        return " " + dec.format(money) + Constants.VND_CURRENCY_UNIT;
    }

    public static String getCurDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.FORMAT_DATE);
        return sdf.format(Calendar.getInstance().getTime());
    }

    public static long getTimeNowSecond() {
        Calendar c = Calendar.getInstance();
        return c.getTimeInMillis() / Constants.MINUTE;
    }

    public static String formatTimeNow(Context context, long timeNowSecond) {
        if (timeNowSecond < Constants.MINUTE)
            return context.getString(R.string.justnow);
        else if (timeNowSecond < Constants.HOUR)
            return (timeNowSecond / Constants.MINUTE) + " " + context.getString(R.string.min_ago);
        else if (timeNowSecond < Constants.DAY)
            return (timeNowSecond / Constants.HOUR) + " " + context.getString(R.string.hour_ago);
        else
            return (timeNowSecond / Constants.DAY) + " " + context.getString(R.string.day_ago);
    }

    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
}
