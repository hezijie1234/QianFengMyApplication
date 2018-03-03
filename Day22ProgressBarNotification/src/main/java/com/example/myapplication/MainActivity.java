package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {
    private NotificationManager mNotiMana;
    private int mProgress;
    public static final int NOTIFICATION_LOAD = 99;
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNotiMana = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mHandler = new Handler();
    }

    public void load(View view) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                RemoteViews rv = new RemoteViews(getPackageName(), R.layout.progressbar);
                rv.setInt(R.id.progress_bar,"setProgress",mProgress);
                rv.setTextViewText(R.id.text_tv,"下载进度"+mProgress+"%");
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);
                Notification notification = builder.setContent(rv).setContentText("应用下载").setSmallIcon(R.mipmap.ic_launcher).
                        setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)).build();
                mNotiMana.notify(NOTIFICATION_LOAD,notification);
                if(mProgress>=100){
                    mNotiMana.cancel(NOTIFICATION_LOAD);
                }else{
                    mProgress+=10;
                    mHandler.postDelayed(this,1000);
                }
            }
        },1000);

    }
}
