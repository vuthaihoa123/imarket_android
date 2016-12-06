package com.example.framgia.imarketandroid.ui.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.FakeContainer;
import com.example.framgia.imarketandroid.data.listener.OnRecyclerItemInteractListener;
import com.example.framgia.imarketandroid.data.model.Floor;
import com.example.framgia.imarketandroid.data.model.Point;
import com.example.framgia.imarketandroid.data.model.SavedPointItem;
import com.example.framgia.imarketandroid.data.model.StoreType;
import com.example.framgia.imarketandroid.data.remote.RealmRemote;
import com.example.framgia.imarketandroid.ui.adapter.CartProductAdapter;
import com.example.framgia.imarketandroid.ui.adapter.ChooseStoreTypeAdapter;
import com.example.framgia.imarketandroid.ui.adapter.SaveLocationAdapter;
import com.example.framgia.imarketandroid.ui.adapter.SavePointAdapter;
import com.example.framgia.imarketandroid.ui.widget.LinearItemDecoration;
import com.example.framgia.imarketandroid.util.Constants;
import com.example.framgia.imarketandroid.util.MapUntils;
import com.example.framgia.imarketandroid.util.SystemUtil;
import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

public class SavePointActivity extends AppCompatActivity implements View.OnClickListener,
    OnRecyclerItemInteractListener {
    private AutoCompleteTextView mEdtSavePoint;
    private TextView mImageButtonSavePoint;
    private RecyclerView mRvPointSaved;
    private SavePointAdapter mPointSavedAdapter;
    private SaveLocationAdapter mLocationAdapter;
    private RecyclerView.LayoutManager mPointSavedLayoutManager;
    public static ArrayList<SavedPointItem> sListPoint = new ArrayList<>();
    public static ArrayList<Point> sListPosition = new ArrayList<>();
    private TextView mDoneButton;
    public static int sCheckpath;
    private Intent mIntent;
    private Point mIntentPoint;
    private ArrayList<StoreType> mListStore = new ArrayList<>();
    private int mControll = FloorActivity.sResumeValue;
    private TextView mTitleSavePoint;
    private LinearLayout mLayoutMain, mLayoutInput;
    private Spinner mSpinner;
    private int mFlagSavePosition = 4;
    private int mFlagSpinner = 5;
    private int mFlagCheckListSave = 7;
    private ArrayAdapter<String> mAdapter;
    private boolean mCheckSelected;
    private static Point sSaveLocaiton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_save_point);
        init();
        final RealmList<Point> listStore = RealmRemote.getListStore();
        ArrayList<Point> listPoint = new ArrayList<Point>();
        mDoneButton = (TextView) findViewById(R.id.btn_done_save_point);
        mDoneButton.setOnClickListener(this);
        if (mControll == mFlagSpinner) {
            DecimalFormat mformat = new DecimalFormat("#.0");
            mTitleSavePoint.setText(R.string.title_save_point);
            for (Point point : listStore) {
                float distance = MapUntils.calculateDistance(new LatLng(mIntentPoint.getLat(),
                    mIntentPoint.getLng()), point);
                if (distance < 25) {
                    listPoint.add(point);
                    StoreType store = new StoreType(0, Constants.LIST_NAME_STORE[point
                        .getType()] + ": \n" + mformat.format(distance) + "m",
                        Constants.LIST_AVATAR_STORE[point.getType()],
                        0);
                    mListStore.add(store);
                }
            }
            mPointSavedAdapter = new SavePointAdapter(this, R.layout.item_choose_store_type,
                mListStore);
            mSpinner.setAdapter(mPointSavedAdapter);
            mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position,
                                           long id) {
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        } else {
        }
    }

    private void init() {
        mLayoutMain = (LinearLayout) findViewById(R.id.layout_main);
        mLayoutInput = (LinearLayout) findViewById(R.id.input_layout);
        mSpinner = (Spinner) findViewById(R.id.spinner_store);
        mSpinner.getBackground().setColorFilter(getResources().getColor(R.color.blue),
                PorterDuff.Mode.SRC_ATOP);
        mIntent = getIntent();
        mIntentPoint = (Point) mIntent.getSerializableExtra(Constants.BUNDLE_SAVE_POINT);
        mTitleSavePoint = (TextView) findViewById(R.id.title_save_point);
        mRvPointSaved = (RecyclerView) findViewById(R.id.rv_list_point_saved);
        mRvPointSaved.addItemDecoration(new LinearItemDecoration(this));
        mRvPointSaved.setHasFixedSize(true);
        if (mControll == mFlagCheckListSave) {
            mLayoutMain.removeView(mLayoutInput);
            mLayoutMain.removeView(mSpinner);
            setRecyclerView();
        }
        if (mControll == mFlagSpinner) {
            mLayoutMain.removeView(mRvPointSaved);
            setAutoCompleteTextView();
            mImageButtonSavePoint = (TextView) findViewById(R.id.img_btn_save_point);
            mImageButtonSavePoint.setOnClickListener(this);
        }
        if (mControll == mFlagSavePosition) {
            mLayoutMain.removeView(mSpinner);
            setAutoCompleteTextView();
            setRecyclerView();
            mImageButtonSavePoint = (TextView) findViewById(R.id.img_btn_save_point);
            mImageButtonSavePoint.setOnClickListener(this);
        }
    }

    private void setRecyclerView() {
        mPointSavedLayoutManager = new LinearLayoutManager(this);
        mRvPointSaved.setLayoutManager(mPointSavedLayoutManager);
        mLocationAdapter = new SaveLocationAdapter(this, sListPoint);
        mLocationAdapter.setOnRecyclerItemInteractListener(this);
        mRvPointSaved.setAdapter(mLocationAdapter);
    }

    private void setAutoCompleteTextView() {
        mEdtSavePoint = (AutoCompleteTextView) findViewById(R.id.edt_save_point);
        mEdtSavePoint.setThreshold(1);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout
            .select_dialog_item, Constants.LIST_NAME_SUGGEST);
        mEdtSavePoint.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_btn_save_point:
                if (mControll == mFlagSavePosition) {
                    String tempNote = mEdtSavePoint.getText().toString();
                    Point point = FloorActivity.sCurrentLocation;
                    int i;
                    if (point != null) {
                        if (sListPoint.size() == 0) {
                            SavedPointItem item =
                                new SavedPointItem(tempNote, false, R.drawable.delete, point);
                            sListPoint.add(item);
                            mLocationAdapter.notifyDataSetChanged();
                        } else {
                            for (i = 0; i < sListPoint.size(); i++) {
                                if (point.getId() == sListPoint.get(i).getmId())
                                    break;
                            }
                            if (i == sListPoint.size()) {
                                SavedPointItem item =
                                    new SavedPointItem(tempNote, false, R.drawable.delete, point);
                                sListPoint.add(item);
                                mLocationAdapter.notifyDataSetChanged();
                            } else
                                Toast.makeText(SavePointActivity.this, R.string.notify_save_point,
                                    Toast
                                        .LENGTH_LONG).show();
                        }
                    }
                    mEdtSavePoint.setText("");
                } else {
                    String tempNote = mEdtSavePoint.getText().toString();
                    SavedPointItem item =
                        new SavedPointItem(tempNote, false, R.drawable.delete, mIntentPoint);
                    sListPoint.add(item);
                    sListPosition.add(mIntentPoint);
                    finish();
                }
                break;
            case R.id.btn_done_save_point:
                finish();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        SavedPointItem item = sListPoint.get(position);
        sCheckpath = 2;
        FloorActivity.sResumeValue = mFlagCheckListSave;
        FloorActivity.sSavedLocation = RealmRemote.getObjectPointFromId(item.getmId());
        FloorActivity.sSavedNote= item.getmNotePoint();
        finish();
    }
}
