package com.example.framgia.imarketandroid.data.model;

import android.view.View;

/**
 * Created by phongtran on 15/09/2016.
 */
public class Showcase {
    private View mViewShowcase;
    private String mTextShowcase;

    public Showcase(View viewShowcase, String textShowcase) {
        mViewShowcase = viewShowcase;
        mTextShowcase = textShowcase;
    }

    public View getViewShowcase() {
        return mViewShowcase;
    }

    public void setViewShowcase(View viewShowcase) {
        this.mViewShowcase = viewShowcase;
    }

    public String getTextShowcase() {
        return mTextShowcase;
    }

    public void setTextShowcase(String textShowcase) {
        this.mTextShowcase = textShowcase;
    }
}
