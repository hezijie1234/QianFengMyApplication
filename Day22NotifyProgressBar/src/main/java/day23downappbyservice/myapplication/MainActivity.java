package day23downappbyservice.myapplication;

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
    private NotificationManager mNotiMan;
    private ProgressBar progressBar;
    private int progress;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNotiMan = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        handler = new Handler();
    }

    public void progressOnClick(View view) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.progressbar);
                remoteViews.setInt(R.id.progress_bar,"setProgress",progress);
                remoteViews.setTextViewText(R.id.load_text,"已下载"+progress+"%");
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);
                Notification notification = builder
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContent(remoteViews)
                        .build();
                notification.flags = Notification.FLAG_AUTO_CANCEL|Notification.FLAG_NO_CLEAR;
                mNotiMan.notify(1,notification);
                if(progress==100){
                    mNotiMan.cancel(1);
                }else {
                    progress+=10;
                    handler.postDelayed(this,1000);
                }
            }
        });

    }
}
