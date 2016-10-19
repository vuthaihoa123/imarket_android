package com.example.framgia.imarketandroid.ui.views;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.framgia.imarketandroid.R;

/**
 * Created by VULAN on 8/11/2016.
 */
public class CustomMarkerView extends FrameLayout {
    private final String FONT = "KaushanScriptRegular.otf";
    public SkewTextView mTextPromotion;
    private double mPercentValue;
    private int mConvertedNumber;
    private RelativeLayout mMarkerBackground;
    private boolean mCheck;

    public void setBackground(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mMarkerBackground.setBackground(drawable);
        }
    }

    public void setInvisibleBackground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mMarkerBackground.setVisibility(INVISIBLE);
        }
    }

    public int getConvertedNumber() {
        return mConvertedNumber;
    }

    public double getPercentValue() {
        return mPercentValue;
    }

    public void setPercentValue(double percentValue) {
        this.mPercentValue = percentValue;
    }

    public CustomMarkerView(Context context) {
        super(context);
        init(context);
        setWillNotDraw(false);
    }

    public CustomMarkerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        setWillNotDraw(false);
    }

    private boolean compareNumber() {
        mConvertedNumber = ((int) mPercentValue);
        if (mPercentValue != mConvertedNumber) {
            return false;
        }
        return true;
    }

    public void setVisible(boolean check) {
        if (check)
            mMarkerBackground.setVisibility(VISIBLE);
        else
            mMarkerBackground.setVisibility(GONE);
    }

    public void init(Context context) {
        View.inflate(context, R.layout.custom_marker, this);
        findView();
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), FONT);
        mTextPromotion.setTypeface(typeface);
    }

    public void setTextforMarker() {
        if (compareNumber()) {
            mTextPromotion.setText("" + getConvertedNumber() + "%");
        } else {
            mTextPromotion.setText("" + getPercentValue() + "%");
        }
    }

    public void findView() {
        mTextPromotion = (SkewTextView) findViewById(R.id.skew_text);
        mMarkerBackground = (RelativeLayout) findViewById(R.id.marker_background);
    }

    public boolean isCheck() {
        return mCheck;
    }

    public void setCheck(boolean mCheck) {
        this.mCheck = mCheck;
    }
}