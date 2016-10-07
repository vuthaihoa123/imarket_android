package com.example.framgia.imarketandroid.util.findpath;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.Floor;
import com.example.framgia.imarketandroid.data.model.ListFloor;
import com.example.framgia.imarketandroid.data.model.UserModel;
import com.example.framgia.imarketandroid.ui.activity.FloorActivity;
import com.example.framgia.imarketandroid.util.Constants;
import com.example.framgia.imarketandroid.util.Flog;
import com.example.framgia.imarketandroid.util.HttpRequest;
import com.example.framgia.imarketandroid.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phongtran on 07/10/2016.
 */
public class LoadDataUtils {
    private static Context mContext;
    private static ProgressDialog mProgressDialog;

    public static void init(Context context) {
        mContext = context;
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage(mContext.getString(R.string.progressdialog));
        mProgressDialog.setProgress(ProgressDialog.STYLE_SPINNER);
        HttpRequest.getInstance().init();
    }

    public static void loadFloor(int idCommerce) {
        mProgressDialog.show();
        HttpRequest.getInstance().loadListFloor(idCommerce);
        HttpRequest.getInstance().setOnLoadDataListener(new HttpRequest.OnLoadDataListener() {
            @Override
            public void onLoadDataSuccess(Object object) {
                ListFloor floors = (ListFloor) object;
                mProgressDialog.dismiss();
                if (floors != null) {
                    Flog.toast(mContext, R.string.data_success);
                }
                    FloorActivity.mFloorList.clear();
                for (int i = 0; i< floors.getFloorList().size(); i++) {
                    Floor floor = floors.getFloorList().get(i);
                    FloorActivity.mFloorList.add(floor.getNameFloor().toString());
                }
            }

            @Override
            public void onLoadDataFailure(String message) {
                mProgressDialog.dismiss();
                Flog.toast(mContext, R.string.no_data);
            }
        });
    }
}
