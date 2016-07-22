package com.example.framgia.imarketandroid.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by hoavt on 21/07/2016.
 */
public class SkewTextView extends TextView {
    private Context mContext;

    public SkewTextView(Context context) {
        super(context);
        mContext = context;
    }

    public SkewTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public SkewTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.skew(1.0f, 0.3f);  //you need to change values over here
        Rotate3dAnimation skew = new Rotate3dAnimation(
                -20, 30, 200, 200, 0, false);   //here too
        startAnimation(skew);

    }
}

