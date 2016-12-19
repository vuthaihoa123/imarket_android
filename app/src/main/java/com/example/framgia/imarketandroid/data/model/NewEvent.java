package com.example.framgia.imarketandroid.data.model;

/**
 * Created by tranphong on 16/12/2016.
 */
public class NewEvent {
    private String mNameEvent;
    private String mContentEvent;
    private String mTimeEvent;

    public NewEvent(String nameEvent, String contentEvent, String timeEvent) {
        mNameEvent = nameEvent;
        mContentEvent = contentEvent;
        mTimeEvent = timeEvent;
    }

    public NewEvent() {
    }

    public String getNameEvent() {
        return mNameEvent;
    }

    public void setNameEvent(String nameEvent) {
        mNameEvent = nameEvent;
    }

    public String getContentEvent() {
        return mContentEvent;
    }

    public void setContentEvent(String contentEvent) {
        mContentEvent = contentEvent;
    }

    public String getTimeEvent() {
        return mTimeEvent;
    }

    public void setTimeEvent(String timeEvent) {
        mTimeEvent = timeEvent;
    }
}
