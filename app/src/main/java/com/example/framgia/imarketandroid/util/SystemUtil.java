package com.example.framgia.imarketandroid.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

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
        return " " + dec.format(money) + "đ";
    }

    public static String getCurDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.FORMAT_DATE);
        return sdf.format(Calendar.getInstance().getTime());
    }

    public static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
}
