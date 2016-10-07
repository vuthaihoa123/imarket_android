package com.example.framgia.imarketandroid.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by hoavt on 18/08/2016.
 */
public class SystemUtil {
    public static String formatMoneyStr(long money) {
        DecimalFormat dec = new DecimalFormat(Constants.FORMAT_MONEY);
        return " " + dec.format(money) + "đ";
    }

    public static String getCurDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.FORMAT_DATE);
        return sdf.format(Calendar.getInstance().getTime());
    }
}
