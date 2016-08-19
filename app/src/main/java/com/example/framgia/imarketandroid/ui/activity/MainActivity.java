package com.example.framgia.imarketandroid.ui.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.services.NotificationEventReceiver;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
    }

    private void findView() {
        findViewById(R.id.button_change_activity).setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_change_activity:
                startActivity(new Intent(this, ChooseMarketActivity.class));
        }
    }

}
