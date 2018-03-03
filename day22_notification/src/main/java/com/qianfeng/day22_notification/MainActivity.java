package com.qianfeng.day22_notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final int NOTIFICATION_NORMAL = 100;
    //通知管理器
    private NotificationManager mNotiMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获得通知管理器
        mNotiMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    //显示普通的通知
    public void onClickNotify(View view){
        //创建通知的创建者
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        //实现通知的动作，跳转到Activity
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setClass(this,MainActivity.class);
        //设置Activity的启动模式为Single_Top
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //设置通知的属性,创建通知对象
        Notification notification = builder
                .setContentTitle("这是标题")
                .setContentText("这是文字内容")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)//设置动作PendingIntent
                .build();
        //给通知设置标记位
        notification.flags = Notification.FLAG_AUTO_CANCEL | Notification.FLAG_NO_CLEAR;
        //通过通知管理器，发送通知
        mNotiMgr.notify(NOTIFICATION_NORMAL,notification);
    }

    public void onClickCancel(View view) {
        //取消通知
        mNotiMgr.cancel(NOTIFICATION_NORMAL);
    }
}
