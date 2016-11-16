package com.example.framgia.imarketandroid.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.StoreType;

import java.util.ArrayList;

/**
 * Created by framgia on 26/10/2016.
 */
public class SavePointAdapter extends ArrayAdapter {
    LayoutInflater mInflater;
    ArrayList<StoreType> mListStore;
    ViewHolder mHolder = null;

    public SavePointAdapter(Context context, int resource, ArrayList objects) {
        super(context, resource, objects);
        mInflater = ((Activity) context).getLayoutInflater();
        mListStore = objects;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        StoreType storeType = mListStore.get(position);
        View row = convertView;
        if (row == null) {
            mHolder = new ViewHolder();
            row = mInflater.inflate(R.layout.item_choose_store_type, parent, false);
            mHolder.name = (TextView) row.findViewById(R.id.tv_name_store);
            mHolder.image = (ImageView) row.findViewById(R.id.iv_avatar_store);
            row.setTag(mHolder);
        } else
            mHolder = (ViewHolder) row.getTag();
        mHolder.name.setText(storeType.getName());
        mHolder.image.setImageResource(storeType.getAvatar());
        return row;
    }

    static class ViewHolder {
        TextView name;
        ImageView image;
    }
}
