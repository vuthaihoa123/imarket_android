package com.example.framgia.imarketandroid.data.model;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.remote.RealmRemote;
import com.example.framgia.imarketandroid.util.Constants;

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

    public SavedPointItem(String notePoint, boolean
        isDeleted, int imageDelete, Point point) {
        this.mId = point.getId();
        if (point.getType() == -1) {
            this.mAvatar = R.drawable.ic_save_point;
            this.mNamePoint = Constants.SAVE_POINT_NAME;
        } else {
            this.mAvatar = Constants.DataList.LIST_AVATAR_STORE[point.getType()];
            this.mNamePoint = Constants.DataList.LIST_NAME_STORE[point.getType()];
        }
        this.mNotePoint = notePoint;
        this.mIsDeleted = isDeleted;
        this.mImageDelete = imageDelete;
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
