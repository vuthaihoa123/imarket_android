package com.example.framgia.imarketandroid.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.Store;

import java.util.List;

/**
 * Created by framgia on 20/09/2016.
 */
public class SpinnerStoreAdapter extends ArrayAdapter<Store> {
    private Context mContext;
    private List<Store> mListStore;
    private int mId;

    public SpinnerStoreAdapter(Context context, int resource,
                               List<Store> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mListStore = objects;
        mId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mId, null);
        }
        Store store = mListStore.get(position);
        ImageView image = (ImageView) convertView.findViewById(R.id.avatar_store_type);
        TextView txtView = (TextView) convertView.findViewById(R.id.name_store_type);
        image.setImageResource(store.getAvatar());
        txtView.setText(store.getName());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mId, null);
        }
        Store store = mListStore.get(position);
        ImageView image = (ImageView) convertView.findViewById(R.id.avatar_store_type);
        TextView txtView = (TextView) convertView.findViewById(R.id.name_store_type);
        image.setImageResource(store.getAvatar());
        txtView.setText(store.getName());
        return convertView;
    }
}
