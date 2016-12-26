package com.example.framgia.imarketandroid.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tranphong on 07/12/2016.
 */
public class EventList {
    @SerializedName("events")
    private List<Event> mEventList;

    public EventList(List<Event> eventList) {
        mEventList = eventList;
    }

    public EventList() {
    }

    public List<Event> getEventList() {
        return mEventList;
    }
}
