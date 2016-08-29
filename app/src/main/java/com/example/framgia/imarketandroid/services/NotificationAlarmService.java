package com.example.framgia.imarketandroid.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.example.framgia.imarketandroid.R;
import com.example.framgia.imarketandroid.data.model.Session;
import com.example.framgia.imarketandroid.ui.activity.ChooseMarketActivity;
import com.example.framgia.imarketandroid.util.Constants;
import com.example.framgia.imarketandroid.util.HttpRequest;

/**
 * Created by toannguyen201194 on 18/08/2016.
 */
public class NotificationAlarmService extends IntentService {
    private static final int NOTIFICATION_ID = 1;
    private String mNameEvent;
    private String mDescription;
    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;

    public NotificationAlarmService() {
        super(Constants.NAMESERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public void ConnectEventApi() {
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        HttpRequest.getInstance().init();
        Context context = this.getApplicationContext();
        notificationManager =
            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent mIntent = new Intent(this, ChooseMarketActivity.class);
        pendingIntent =
            PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        HttpRequest.getInstance().requestEventNocation();
        HttpRequest.getInstance().setOnLoadDataListener(new HttpRequest.OnLoadDataListener() {
            @Override
            public void onLoadDataSuccess(Object object) {
                Session event = (Session) object;
                mNameEvent = event.getFullname();
                mDescription = event.getUsername();
                NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(getBaseContext());
                Resources res = getBaseContext().getResources();
                builder.setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.avatar)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.avatar))
                    .setAutoCancel(true)
                    .setContentTitle(mNameEvent)
                    .setContentText(mDescription);
                notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(NOTIFICATION_ID, builder.build());
            }

            @Override
            public void onLoadDataFailure(String message) {
            }
        });
    }
}
