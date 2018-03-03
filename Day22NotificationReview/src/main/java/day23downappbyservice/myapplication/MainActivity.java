package day23downappbyservice.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private NotificationManager mNotiMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNotiMan = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    public void onClickNotify(View view) {
        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(this);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setClass(this,Main2Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pend = PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = notificationCompat.setContentTitle("这是标题").setContentText("这是内容").
                setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pend)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)).build();
        mNotiMan.notify(1,notification);
    }

    public void onClickCancle(View view) {
        mNotiMan.cancel(1);
    }
}
