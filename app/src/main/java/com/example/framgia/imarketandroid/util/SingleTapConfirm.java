package com.example.framgia.imarketandroid.util;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by framgia on 14/11/2016.
 */
public class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {
    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        return true;
    }
}
