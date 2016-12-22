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
    private float mSkewX = 1.0f;
    private float mSkewY = 0.3f;
    private float mFromDegree = -20;
    private float mToDegree = 30;
    private float mCenterX = 200;
    private float mCenterY = 200;
    private float mDepthZ = 0;

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
        canvas.skew(mSkewX, mSkewY);  //you need to change values over here
        Rotate3dAnimation skew = new Rotate3dAnimation(
            mFromDegree, mToDegree, mCenterX, mCenterY, mDepthZ, false);   //here too
        startAnimation(skew);
    }
}

