package com.example.framgia.imarketandroid.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by hoavt on 25/08/2016.
 */
public class SpinnerArrayAdapter extends ArrayAdapter<String> {
    private int mHidingItemIndex;

    public SpinnerArrayAdapter(Context context, int textViewResourceId, String[] objects,
                               int hidingItemIndex) {
        super(context, textViewResourceId, objects);
        this.mHidingItemIndex = hidingItemIndex;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View v = null;
        if (position == mHidingItemIndex) {
            TextView tv = new TextView(getContext());
            tv.setVisibility(View.GONE);
            v = tv;
        } else {
            v = super.getDropDownView(position, null, parent);
        }
        return v;
    }
}
