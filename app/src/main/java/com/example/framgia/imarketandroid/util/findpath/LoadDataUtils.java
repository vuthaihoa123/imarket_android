package com.example.framgia.imarketandroid.util.findpath;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.CommerceCanter;
import com.example.framgia.imarketandroid.data.model.CommerceList;
import com.example.framgia.imarketandroid.data.model.Floor;
import com.example.framgia.imarketandroid.data.model.ListFloor;
import com.example.framgia.imarketandroid.data.model.Stores;
import com.example.framgia.imarketandroid.ui.activity.ChooseMarketActivity;
import com.example.framgia.imarketandroid.ui.activity.FloorActivity;
import com.example.framgia.imarketandroid.util.Flog;
import com.example.framgia.imarketandroid.util.HttpRequest;

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
                    FloorActivity.mFloorList.clear();
                    for (int i = 0; i < floors.getFloorList().size(); i++) {
                        Floor floor = floors.getFloorList().get(i);
                        FloorActivity.mFloorList.add(floor.getNameFloor().toString());
                    }
                } else {
                    Flog.toast(mContext, R.string.not_data_in_object);
                }
            }

            @Override
            public void onLoadDataFailure(String message) {
                mProgressDialog.dismiss();
                Flog.toast(mContext, R.string.no_data);
            }
        });
    }

    public static void loadCommerce() {
        mProgressDialog.show();
        HttpRequest.getInstance().loadListCommerce();
        HttpRequest.getInstance().setOnLoadDataListener(new HttpRequest.OnLoadDataListener() {
            @Override
            public void onLoadDataSuccess(Object object) {
                CommerceList commerceList = (CommerceList) object;
                mProgressDialog.dismiss();
                if (commerceList != null) {
                    Flog.toast(mContext, R.string.data_success);
                    ChooseMarketActivity.sMarkets.clear();
                    int size = commerceList.getCenterList().size();
                    for (int i = 0; i < size; i++) {
                        CommerceCanter commerceCanter = commerceList.getCenterList().get(i);
                        ChooseMarketActivity.sMarkets.add(commerceCanter);
                    }
                } else {
                    Flog.toast(mContext, R.string.not_data_in_object);
                }
            }

            @Override
            public void onLoadDataFailure(String message) {
                mProgressDialog.dismiss();
                Flog.toast(mContext, R.string.no_data);
            }
        });
    }

    public static void getStoreByStoreType(int id_floor, int storeType) {
        mProgressDialog.show();
        HttpRequest.getInstance().getStore(id_floor, storeType);
        HttpRequest.getInstance().setOnLoadDataListener(new HttpRequest.OnLoadDataListener() {
            @Override
            public void onLoadDataSuccess(Object object) {
                // data store
                Stores listStore = (Stores) object;
                mProgressDialog.dismiss();
                if (listStore != null) {
                    Flog.toast(mContext, R.string.store_success);
                } else {
                    Flog.toast(mContext, R.string.store_null);
                }
            }

            @Override
            public void onLoadDataFailure(String message) {
                mProgressDialog.dismiss();
            }
        });
    }
    //data store
//    LoadDataUtils.init(getContext());
//    LoadDataUtils.getStoreByStoreType(1,1);
}
