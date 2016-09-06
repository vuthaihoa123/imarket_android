package com.example.framgia.imarketandroid.ui.views;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.framgia.imarketandroid.R;

/**
 * Created by VULAN on 8/11/2016.
 */
public class CustomMarkerView extends FrameLayout {
    private final String FONT = "KaushanScriptRegular.otf";
    public TextView mTextCenter;
    private double mPercentValue;
    private int mConvertedNumber;
    private FrameLayout mFrameLayout;

    public void setBackground(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mFrameLayout.setBackground(drawable);
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
            mFrameLayout.setVisibility(VISIBLE);
        else
            mFrameLayout.setVisibility(GONE);
    }

    public void init(Context context) {
        View.inflate(context, R.layout.custom_marker, this);
        findView();
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), FONT);
        mTextCenter.setTypeface(typeface);
    }

    public void setTextforMarker() {
        if (compareNumber()) {
            mTextCenter.setText("" + getConvertedNumber() + "%");
        } else {
            mTextCenter.setText("" + getPercentValue() + "%");
        }
    }

    public void findView() {
        mTextCenter = (TextView) findViewById(R.id.text_center);
        mFrameLayout = (FrameLayout) findViewById(R.id.marker_background);
    }
}