package com.example.myapplication;

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
    private NotificationManager mNofiMana;
    public static final int NOTIFICATION_TEST = 88;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNofiMana = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    public void notify(View view) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setClass(this,NewActivity.class);
        PendingIntent pend = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = builder.setContentTitle("标题").setContentText("内容").setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)).
                        setContentIntent(pend).build();
        mNofiMana.notify(NOTIFICATION_TEST,notification);
    }

    public void cancle(View view) {
        mNofiMana.cancel(NOTIFICATION_TEST);
    }
}
