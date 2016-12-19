package com.example.framgia.imarketandroid.util.findpath;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.Toast;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.listener.OnFinishLoadDataListener;
import com.example.framgia.imarketandroid.data.model.CommerceCanter;
import com.example.framgia.imarketandroid.data.model.CommerceList;
import com.example.framgia.imarketandroid.data.model.Event;
import com.example.framgia.imarketandroid.data.model.EventList;
import com.example.framgia.imarketandroid.data.model.Floor;
import com.example.framgia.imarketandroid.data.model.ItemProduct;
import com.example.framgia.imarketandroid.data.model.ListFloor;
import com.example.framgia.imarketandroid.data.model.ProductList;
import com.example.framgia.imarketandroid.data.model.Stores;
import com.example.framgia.imarketandroid.ui.activity.FloorActivity;
import com.example.framgia.imarketandroid.util.Constants;
import com.example.framgia.imarketandroid.util.Flog;
import com.example.framgia.imarketandroid.util.HttpRequest;

import java.util.List;

/**
 * Created by phongtran on 07/10/2016.
 */
public class LoadDataUtils {
    private  Context mContext;
    private  ProgressDialog mProgressDialog;
    public  BroadcastReceiver mReceiver;
    private OnFinishLoadDataListener mFinishLoadDataListener;

    public void init(Context context) {
        mContext = context;
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage(mContext.getString(R.string.progressdialog));
        mProgressDialog.setProgress(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCanceledOnTouchOutside(false);
        HttpRequest.getInstance(context).init();
    }

    public interface OnLoadMarketsCallback {
        void onLoadMarketsDone(List<CommerceCanter> list);
    }

    private OnLoadMarketsCallback mLoadMarketsListenner;
    private OnListProductListener mOnListProductListener;

    public LoadDataUtils setLoadMarketsListenner(OnLoadMarketsCallback loadMarketsListenner) {
        mLoadMarketsListenner = loadMarketsListenner;
        return this;
    }

    public void setOnListProductListener(OnListProductListener mOnListProductListener) {
        this.mOnListProductListener = mOnListProductListener;
    }

    public  void loadFloor(final Context context, final int idCommerce) {
        init(context);
        mProgressDialog.show();
        HttpRequest.getInstance(context).loadListFloor(idCommerce);
        HttpRequest.getInstance(context).setOnLoadDataListener(new HttpRequest.OnLoadDataListener() {
            @Override
            public void onLoadDataSuccess(Object object) {
                ListFloor floors = (ListFloor) object;
                mProgressDialog.dismiss();
                if (floors != null) {
                    FloorActivity.sFloorList.clear();
                    for (int i = 0; i < floors.getFloorList().size(); i++) {
                        Floor floor = floors.getFloorList().get(i);
                        FloorActivity.sFloorList.add(floor.getNameFloor().toString());
                    }
                    mFinishLoadDataListener.onFinish(Constants.ResultFinishLoadData.LOAD_DATA_FINISH);
                } else {
                    Flog.toast(mContext, R.string.not_data_in_object);
                }
            }

            @Override
            public void onLoadDataFailure(String message) {
                mProgressDialog.dismiss();
                if (!InternetUtil.isInternetConnected(context)) {
                    Flog.toast(context, R.string.no_internet);
                    processBroadcastFloor(idCommerce);
                }
            }
        });
    }


    public void loadCommerce(final Context context, final List<CommerceCanter> list,
                             final View viewRecycle, final View viewImage) {
        init(context);
        mProgressDialog.show();
        HttpRequest.getInstance(context).loadListCommerce();
        HttpRequest.getInstance(context).setOnLoadDataListener(new HttpRequest.OnLoadDataListener() {
            @Override
            public void onLoadDataSuccess(Object object) {
                mProgressDialog.dismiss();
                CommerceList commerceList = (CommerceList) object;
                if (commerceList != null) {
                    list.clear();
                    int size = commerceList.getCenterList().size();
                    for (int i = 0; i < size; i++) {
                        CommerceCanter commerceCanter = commerceList.getCenterList().get(i);
                        list.add(commerceCanter);
                    }
                    viewRecycle.setVisibility(View.VISIBLE);
                    viewImage.setVisibility(View.GONE);
                    mFinishLoadDataListener.onFinish(Constants.ResultFinishLoadData.LOAD_DATA_FINISH);
                } else {
                    Flog.toast(mContext, R.string.not_data_in_object);
                }
            }

            @Override
            public void onLoadDataFailure(String message) {
                mProgressDialog.dismiss();
                if (!InternetUtil.isInternetConnected(context)) {
                    Flog.toast(context, R.string.no_internet);
                    viewImage.setVisibility(View.VISIBLE);
                    viewRecycle.setVisibility(View.GONE);
                    processBroadcastCommerce(context, list, viewRecycle, viewImage);
                }
            }
        });
    }

    public void setLoadDataListener(OnFinishLoadDataListener listener) {
        mFinishLoadDataListener = listener;
    }

    public void getStoreByStoreType(final Context context, final int id_floor, final int storeType) {
        init(context);
        mProgressDialog.show();
        HttpRequest.getInstance(context).getStore(id_floor, storeType);
        HttpRequest.getInstance(context).setOnLoadDataListener(new HttpRequest.OnLoadDataListener() {
            @Override
            public void onLoadDataSuccess(Object object) {
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
                if (!InternetUtil.isInternetConnected(context)) {
                    Flog.toast(context, R.string.no_internet);
                    processBroadcastStore(id_floor, storeType);
                }
            }
        });
    }

    public void getProductInCategory(final Context context, final int id_cate) {
        init(context);
        mProgressDialog.show();
        HttpRequest.getInstance(context).getProduct(id_cate);
        HttpRequest.getInstance(context).setOnLoadDataListener(new HttpRequest.OnLoadDataListener() {
            @Override
            public void onLoadDataSuccess(Object object) {
                if (!(object instanceof ProductList)) {
                    return;
                }
                ProductList productList = (ProductList) object;
                if (mOnListProductListener != null) {
                    if (productList != null) {
                        mOnListProductListener.onListReceiver(productList.getItemProductList());
                        if(productList.getItemProductList().size()==0){
                            Flog.toast(mContext, R.string.product_null);
                        }
                    } else {
                        Flog.toast(mContext, R.string.product_null);
                    }
                }
                mProgressDialog.dismiss();
            }

            @Override
            public void onLoadDataFailure(String message) {
                mProgressDialog.dismiss();
                if (!InternetUtil.isInternetConnected(context)) {
                    Flog.toast(context, R.string.no_internet);
                    processBroadcastProduct(id_cate);
                }
            }
        });
    }

    public void getEvents (final Context context, final int id_store, final List<Event> lists) {
        init(context);
        HttpRequest.getInstance(context).getEvents(id_store);
        HttpRequest.getInstance(context).setOnLoadDataListener(
            new HttpRequest.OnLoadDataListener() {
                @Override
                public void onLoadDataSuccess(Object object) {
                    if (!(object instanceof EventList)) return;
                    EventList eventList = (EventList) object;
                    lists.clear();
                    if (eventList != null) {
                        for (int i = 0; i < eventList.getEventList().size(); i++) {
                            Event event = eventList.getEventList().get(i);
                            lists.add(event);
                        }
                        mFinishLoadDataListener.onFinish(
                            Constants.ResultFinishLoadData.LOAD_EVENT_FINISH);
                    }
                }

                @Override
                public void onLoadDataFailure(String message) {
                }
            });
    }

    private void processBroadcastProduct(final int id_cate) {
        IntentFilter filter = new IntentFilter(Constants.INTERNET_FILTER);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, R.string.connect_change, Toast.LENGTH_SHORT).show();
                if (InternetUtil.isInternetConnected(mContext)) {
                    getProductInCategory(mContext, id_cate);
                }
            }
        };
        mContext.registerReceiver(mReceiver, filter);
    }

    private void processBroadcastCommerce(final Context conText, final List<CommerceCanter> list,
                                          final View viewCycle, final View viewImage) {
        IntentFilter filter = new IntentFilter(Constants.INTERNET_FILTER);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, R.string.connect_change, Toast.LENGTH_SHORT).show();
                if (InternetUtil.isInternetConnected(conText)) {
                    loadCommerce(conText, list, viewCycle, viewImage);
                }
            }
        };
        mContext.registerReceiver(mReceiver, filter);
    }

    private void processBroadcastStore(final int id_floor, final int storeType) {
        IntentFilter filter = new IntentFilter(Constants.INTERNET_FILTER);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, R.string.connect_change, Toast.LENGTH_SHORT).show();
                if (InternetUtil.isInternetConnected(mContext)) {
                    getStoreByStoreType(mContext, id_floor, storeType);
                }
            }
        };
        mContext.registerReceiver(mReceiver, filter);
    }

    private void processBroadcastFloor(final int commerce) {
        IntentFilter filter = new IntentFilter(Constants.INTERNET_FILTER);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, R.string.connect_change, Toast.LENGTH_SHORT).show();
                if (InternetUtil.isInternetConnected(mContext)) {
                    loadFloor(mContext, commerce);
                }
            }
        };
        mContext.registerReceiver(mReceiver, filter);
    }

    public interface OnListProductListener {
        void onListReceiver(List<ItemProduct> list);
    }
}
