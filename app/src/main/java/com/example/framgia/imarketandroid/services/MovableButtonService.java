package com.example.framgia.imarketandroid.services;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.MyViewGroup;
import com.example.framgia.imarketandroid.ui.activity.FloorActivity;
import com.example.framgia.imarketandroid.ui.activity.SavePointActivity;
import com.getbase.floatingactionbutton.FloatingActionButton;

/**
 * Created by xuantung on 09/11/2016.
 */
public class MovableButtonService extends Service
    implements View.OnTouchListener, View.OnClickListener {
    private WindowManager mWindowManager;
    private MyViewGroup mMyViewGroup;
    private WindowManager.LayoutParams mParams;
    private View mSubView;
    private int DOWN_X, DOWN_Y, MOVE_X, MOVE_Y, xparam, yparam;
    private FloatingActionButton mIBNearPoint, mIBDirection;

    @Override
    public void onCreate() {
        super.onCreate();
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    private void initView() {
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mMyViewGroup = new MyViewGroup(this);
        LayoutInflater minflater = LayoutInflater.from(this);
        mSubView = minflater
            .inflate(R.layout.activity_move_button, mMyViewGroup);
        mParams = new WindowManager.LayoutParams();
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.gravity = Gravity.CENTER;
        mParams.format = PixelFormat.TRANSLUCENT;//trong suot
        mParams.type = WindowManager.LayoutParams.TYPE_PHONE;// noi tren all be mat
        mParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;// khong bi gioi han boi man hinh|Su duoc nut home
        mWindowManager.addView(mMyViewGroup, mParams);
        mSubView.setOnTouchListener(this);
        mIBNearPoint = (FloatingActionButton) mSubView.findViewById(R.id.near_point);
        mIBDirection = (FloatingActionButton) mSubView.findViewById(R.id.direction);
        mIBDirection.setOnClickListener(this);
        mIBNearPoint.setOnClickListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xparam = mParams.x;
                yparam = mParams.y;
                DOWN_X = (int) event.getRawX();
                DOWN_Y = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                MOVE_X = (int) event.getRawX() - DOWN_X;
                MOVE_Y = (int) event.getRawY() - DOWN_Y;
                updateView(MOVE_X, MOVE_Y);
                break;
        }
        return true;
    }

    private void updateView(int x, int y) {
        mParams.x = x + xparam;
        mParams.y = y + yparam;
        mWindowManager.updateViewLayout(mMyViewGroup, mParams);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
