package com.example.framgia.imarketandroid.util;

import java.text.DecimalFormat;

/**
 * Created by hoavt on 18/08/2016.
 */
public class SystemUtil {
    public static String formatMoneyStr(long money) {
        DecimalFormat dec = new DecimalFormat("###,###,###,###,###.##");
        return " " + dec.format(money) + "đ";
    }
}
