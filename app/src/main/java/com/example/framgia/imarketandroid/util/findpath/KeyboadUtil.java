package com.example.framgia.imarketandroid.util.findpath;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;

/**
 * Created by phongtran on 25/11/2016.
 */
public class KeyboadUtil {
    public static void showKeyboard(Activity activity, AutoCompleteTextView view) {
        view.setFocusable(true);
        InputMethodManager imm =
            (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideKeyboard(Activity activity) {
        // Check if no view has focus:
        InputMethodManager inputManager =
            (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(
            activity.getCurrentFocus().getWindowToken(),
            InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
