package com.example.framgia.imarketandroid.data.model;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by phongtran on 26/08/2016.
 */
public class MessageSuggestStore {
    private int mImageViewAva;
    private String mTextViewContent;
    private String mNameUser;
    private int mImageViewStar1;
    private int mImageViewStar2;
    private int mImageViewStar3;
    private int mImageViewStar4;
    private int mImageViewStar5;

    public MessageSuggestStore() {
    }

    public MessageSuggestStore(int mImageViewAva,
                               String mTextViewContent,
                               String nameUser,
                               int mImageViewStar1,
                               int mImageViewStar2,
                               int mImageViewStar3,
                               int mImageViewStar4,
                               int mImageViewStar5) {
        this.mImageViewAva = mImageViewAva;
        this.mTextViewContent = mTextViewContent;
        this.mNameUser = nameUser;
        this.mImageViewStar1 = mImageViewStar1;
        this.mImageViewStar2 = mImageViewStar2;
        this.mImageViewStar3 = mImageViewStar3;
        this.mImageViewStar4 = mImageViewStar4;
        this.mImageViewStar5 = mImageViewStar5;
    }

    public int getmImageViewAva() {
        return mImageViewAva;
    }

    public void setmImageViewAva(int mImageViewAva) {
        this.mImageViewAva = mImageViewAva;
    }

    public String getmTextViewContent() {
        return mTextViewContent;
    }

    public void setmTextViewContent(String mTextViewContent) {
        this.mTextViewContent = mTextViewContent;
    }

    public int getmImageViewStar1() {
        return mImageViewStar1;
    }

    public void setmImageViewStar1(int mImageViewStar1) {
        this.mImageViewStar1 = mImageViewStar1;
    }

    public int getmImageViewStar2() {
        return mImageViewStar2;
    }

    public void setmImageViewStar2(int mImageViewStar2) {
        this.mImageViewStar2 = mImageViewStar2;
    }

    public int getmImageViewStar3() {
        return mImageViewStar3;
    }

    public void setmImageViewStar3(int mImageViewStar3) {
        this.mImageViewStar3 = mImageViewStar3;
    }

    public int getmImageViewStar4() {
        return mImageViewStar4;
    }

    public void setmImageViewStar4(int mImageViewStar4) {
        this.mImageViewStar4 = mImageViewStar4;
    }

    public int getmImageViewStar5() {
        return mImageViewStar5;
    }

    public void setmImageViewStar5(int mImageViewStar5) {

        this.mImageViewStar5 = mImageViewStar5;
    }

    public String getmNameUser() {
        return mNameUser;
    }

    public void setmNameUser(String mNameUser) {
        this.mNameUser = mNameUser;
    }
}
