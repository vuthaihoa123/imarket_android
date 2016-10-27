package com.example.framgia.imarketandroid.data.model;

import com.example.framgia.imarketandroid.data.remote.RealmRemote;

import io.realm.RealmObject;

/**
 * Created by framgia on 26/10/2016.
 */
public class SavedPointItem extends RealmObject {
    private int mId;
    private int mAvatar;
    private String mNamePoint;
    private String mNotePoint;
    private int mImageDelete;
    private boolean mIsDeleted = false;

    public SavedPointItem() {
    }

    public SavedPointItem(int mId, int mAvatar, String mNamePoint, String mNotePoint, boolean
        mIsDeleted, int mImageDelete) {
        this.mId=mId;
        this.mAvatar = mAvatar;
        this.mNamePoint = mNamePoint;
        this.mNotePoint = mNotePoint;
        this.mIsDeleted = mIsDeleted;
        this.mImageDelete = mImageDelete;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getmAvatar() {
        return mAvatar;
    }

    public void setmAvatar(int mAvatar) {
        this.mAvatar = mAvatar;
    }

    public String getmNamePoint() {
        return mNamePoint;
    }

    public void setmNamePoint(String mNamePoint) {
        this.mNamePoint = mNamePoint;
    }

    public String getmNotePoint() {
        return mNotePoint;
    }

    public void setmNotePoint(String mNotePoint) {
        this.mNotePoint = mNotePoint;
    }

    public int getmImageDelete() {
        return mImageDelete;
    }

    public void setmImageDelete(int mImageDelete) {
        this.mImageDelete = mImageDelete;
    }

    public void setIsDeleted(boolean isDeleted) {
        mIsDeleted = isDeleted;
    }
}
