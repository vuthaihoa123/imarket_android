package com.example.framgia.imarketandroid.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.FakeContainer;
import com.example.framgia.imarketandroid.data.listener.OnRecyclerItemInteractListener;
import com.example.framgia.imarketandroid.data.model.Floor;
import com.example.framgia.imarketandroid.data.model.Point;
import com.example.framgia.imarketandroid.data.model.SavedPointItem;
import com.example.framgia.imarketandroid.ui.adapter.CartProductAdapter;
import com.example.framgia.imarketandroid.ui.adapter.SavePointAdapter;
import com.example.framgia.imarketandroid.ui.widget.LinearItemDecoration;
import com.example.framgia.imarketandroid.util.Constants;
import com.example.framgia.imarketandroid.util.SystemUtil;

import java.util.ArrayList;
import java.util.List;

public class SavePointActivity extends AppCompatActivity implements View.OnClickListener,
    OnRecyclerItemInteractListener {
    private EditText mEdtSavePoint;
    private ImageButton mImageButtonSavePoint;
    private RecyclerView mRvPointSaved;
    private SavePointAdapter mPointSavedAdapter;
    private RecyclerView.LayoutManager mPointSavedLayoutManager;
    public static ArrayList<SavedPointItem> sListPoint = new ArrayList<>();
    private Button mDoneButton;
    public static int mCheckpath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_point);
        mEdtSavePoint = (EditText) findViewById(R.id.edt_save_point);
        mImageButtonSavePoint = (ImageButton) findViewById(R.id.img_save_point);
        mImageButtonSavePoint.setOnClickListener(this);
        mDoneButton = (Button) findViewById(R.id.btn_done_save_point);
        mDoneButton.setOnClickListener(this);
        mRvPointSaved = (RecyclerView) findViewById(R.id.rv_list_point_saved);
        mRvPointSaved.addItemDecoration(new LinearItemDecoration(this));
        mRvPointSaved.setHasFixedSize(true);
        mPointSavedLayoutManager = new LinearLayoutManager(this);
        mRvPointSaved.setLayoutManager(mPointSavedLayoutManager);
        mPointSavedAdapter = new SavePointAdapter(this, sListPoint);
        mPointSavedAdapter.setOnRecyclerItemInteractListener(this);
        mRvPointSaved.setAdapter(mPointSavedAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_save_point:
                String tempNote = mEdtSavePoint.getText().toString();
                Point point = FloorActivity.sCurrentLocation;
                int i;
                if (point != null) {
                    if (sListPoint.size() == 0) {
                        SavedPointItem item = new SavedPointItem(point.getId(), Constants
                            .LIST_AVATAR_STORE[point
                            .getType()], Constants.LIST_NAME_STORE[point.getType()], tempNote,
                            false, R
                            .drawable.delete);
                        sListPoint.add(item);
                        mPointSavedAdapter.notifyDataSetChanged();
                    } else {
                        for (i = 0; i < sListPoint.size(); i++) {
                            if (point.getId() == sListPoint.get(i).getmId())
                                break;
                        }
                        if (i == sListPoint.size()) {
                            SavedPointItem item = new SavedPointItem(point.getId(), Constants
                                .LIST_AVATAR_STORE[point
                                .getType()], Constants.LIST_NAME_STORE[point.getType()], tempNote,
                                false, R
                                .drawable.delete);
                            sListPoint.add(item);
                            mPointSavedAdapter.notifyDataSetChanged();
                        } else
                            Toast.makeText(SavePointActivity.this, R.string.notify_save_point, Toast
                                .LENGTH_LONG).show();
                    }
                }
                mEdtSavePoint.setText("");
                break;
            case R.id.btn_done_save_point:
                finish();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        SavedPointItem item=sListPoint.get(position);
        mCheckpath=-1;
        if(item.getmId()==FloorActivity.sCurrentLocation.getId())
            finish();
        else{
            mCheckpath=item.getmId();
            finish();
        }

    }
}
