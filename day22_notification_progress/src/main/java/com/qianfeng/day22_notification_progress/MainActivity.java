package com.qianfeng.day22_notification_progress;

import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {

    public static final int NOTIFICATION_PROGRESS = 1000;
    private NotificationManager mManager = null;
    private int mProgress = 0;
    private Handler mHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mHandler = new Handler();
    }

    public void onClickProgress(View view) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //使用远程视图加载自定义布局
                RemoteViews rv = new RemoteViews(getPackageName(),R.layout.progress_notif);
                //设置进度值
                rv.setInt(R.id.progress_bar,"setProgress",mProgress);
                rv.setTextColor(R.id.progress_tv, Color.RED);
                rv.setTextViewText(R.id.progress_tv,"当前进度:"+mProgress+"%");
                //创建通知,必须要有小图标
                Notification notification = new NotificationCompat.Builder(MainActivity.this)
                        .setContentTitle("当前下载进度")
                        .setContentText("进度")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContent(rv)
                        .build();
                //发出通知
                mManager.notify(NOTIFICATION_PROGRESS,notification);
                if(mProgress >= 100){
                    mManager.cancel(NOTIFICATION_PROGRESS);
                }else{
                    mProgress += 10;
                    mHandler.postDelayed(this,1000);
                }
            }
        });

    }
}
