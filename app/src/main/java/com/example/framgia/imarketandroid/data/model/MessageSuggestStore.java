package com.example.framgia.imarketandroid.data.model;

/**
 * Created by phongtran on 26/08/2016.
 */
public class MessageSuggestStore {
    private int mImageViewAva;
    private String mTextViewContent;
    private String mTextViewTitle;
    private String mNameUser;
    private String mCurDate;
    private int mImageViewStar1;
    private int mImageViewStar2;
    private int mImageViewStar3;
    private int mImageViewStar4;
    private int mImageViewStar5;

    public MessageSuggestStore() {
    }

    public MessageSuggestStore(int avatar,
                               String title,
                               String content,
                               String nameUser,
                               String curDate,
                               int imageViewStar1,
                               int imageViewStar2,
                               int imageViewStar3,
                               int imageViewStar4,
                               int imageViewStar5) {
        this.mCurDate = curDate;
        this.mTextViewTitle = title;
        this.mImageViewAva = avatar;
        this.mTextViewContent = content;
        this.mNameUser = nameUser;
        this.mImageViewStar1 = imageViewStar1;
        this.mImageViewStar2 = imageViewStar2;
        this.mImageViewStar3 = imageViewStar3;
        this.mImageViewStar4 = imageViewStar4;
        this.mImageViewStar5 = imageViewStar5;
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

    public int getImageViewStar1() {
        return mImageViewStar1;
    }

    public void setImageViewStar1(int imageViewStar1) {
        this.mImageViewStar1 = imageViewStar1;
    }

    public int getImageViewStar2() {
        return mImageViewStar2;
    }

    public void setImageViewStar2(int imageViewStar2) {
        this.mImageViewStar2 = imageViewStar2;
    }

    public int getImageViewStar3() {
        return mImageViewStar3;
    }

    public void setImageViewStar3(int imageViewStar3) {
        this.mImageViewStar3 = imageViewStar3;
    }

    public int getImageViewStar4() {
        return mImageViewStar4;
    }

    public void setImageViewStar4(int imageViewStar4) {
        this.mImageViewStar4 = imageViewStar4;
    }

    public int getImageViewStar5() {
        return mImageViewStar5;
    }

    public void setImageViewStar5(int imageViewStar5) {

        this.mImageViewStar5 = imageViewStar5;
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
}
