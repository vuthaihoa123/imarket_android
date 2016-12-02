package com.example.framgia.imarketandroid.data.model;

/**
 * Created by VULAN on 10/12/2016.
 */

public class Comment {
    private int mImageViewAva;
    private String mTextViewContent;
    private String mTextViewTitle;
    private String mNameUser;
    private String mCurDate;
    private long mTimeNow = 0;
    private int mTotalStar;

    public Comment() {
    }

    public Comment(int avatar,
                   String title,
                   String content,
                   String nameUser,
                   String curDate) {
        mCurDate = curDate;
        mTextViewTitle = title;
        mImageViewAva = avatar;
        mTextViewContent = content;
        mNameUser = nameUser;
        init();
    }

    public Comment(int avatar,
                   String title,
                   String content,
                   String nameUser,
                   long timeNow) {
        mTimeNow = timeNow;
        mTextViewTitle = title;
        mImageViewAva = avatar;
        mTextViewContent = content;
        mNameUser = nameUser;
        init();
    }

    private void init() {
        mTotalStar = 1;
    }

    public void setImageStars(int totalFullStar) {
        mTotalStar = totalFullStar;
    }

    public int getTotalStar(){
        return mTotalStar;
    }
    public int getImageViewAvatar() {
        return mImageViewAva;
    }

    public void setImageViewAva(int imageViewAva) {
        this.mImageViewAva = imageViewAva;
    }

    public String getTextViewContent() {
        return mTextViewContent;
    }

    public String getTextViewTitle() {
        return mTextViewTitle;
    }

    public void setTextViewContent(String textViewContent) {
        this.mTextViewContent = textViewContent;
    }

    public String getNameUser() {
        return mNameUser;
    }

    public void setNameUser(String nameUser) {
        this.mNameUser = nameUser;
    }

    public String getCurDate() {
        return mCurDate;
    }

    public long getTimeNow() {
        return mTimeNow;
    }
}
