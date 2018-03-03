package com.qianfeng.day23_service_download;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String WEIXIN_URL = "http://sj.img4399.com/game_list/47/com.wepie.snake/wepie.snake.v117518.apk?ref=news";
    public static final String ACTION_PROGRESS = "ACTION_PROGRESS";
    public static final String ACTION_FINISH = "ACTION_FINISH";
    private ProgressBar mDownloadPb;
    private LocalBroadcastManager mLbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLbm = LocalBroadcastManager.getInstance(this);
        //注册广播接收器
        IntentFilter filter = new IntentFilter(ACTION_PROGRESS);
        filter.addAction(ACTION_FINISH);
        mLbm.registerReceiver(mReceiver,filter);
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLbm.unregisterReceiver(mReceiver);
    }

    private void initViews() {
        mDownloadPb = (ProgressBar) findViewById(R.id.download_pb);
    }

    public void onStartDownload(View view) {
        //启动Service开始下载
        Intent intent = new Intent(this,DownloadService.class);
        intent.putExtra("file_path",WEIXIN_URL);
        intent.putExtra("file_name","mysnake.apk");
        startService(intent);
    }

    /**
     * 接收器，接受Service发送的进度广播
     */
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(ACTION_PROGRESS.equals(intent.getAction())){
                //更新进度条
                int progress = intent.getIntExtra("progress",0);
                mDownloadPb.setProgress(progress);
            }else if(ACTION_FINISH.equals(intent.getAction())){
                Toast.makeText(context, "文件下载完成，保存在"+intent.getStringExtra("file_path"),
                        Toast.LENGTH_SHORT).show();
            }
        }
    };
}
