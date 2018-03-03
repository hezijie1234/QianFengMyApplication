package com.qianfeng.day29_download_continue;

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

import com.qianfeng.day29_download_continue.entities.DownloadInfo;
import com.qianfeng.day29_download_continue.services.DownloadService;

public class MainActivity extends AppCompatActivity {


    public static final String ACTION_PROGRESS = "ACTION_PROGRESS";
    public static final String ACTION_COMPLETE = "ACTION_COMPLETE";
    public static final String FILE_URL = "http://music.baidu.com/cms/BaiduMusic-pcwebdownload.apk";
    public static final String FILE_NAME = "BaiduMusic.apk";
    private LocalBroadcastManager mLbm;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        //注册广播接收器
        mLbm = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_COMPLETE);
        intentFilter.addAction(ACTION_PROGRESS);
        mLbm.registerReceiver(mDownloadReceiver,intentFilter);
    }

    private void initViews() {
        mProgressBar = (ProgressBar)findViewById(R.id.download_pb);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLbm.unregisterReceiver(mDownloadReceiver);
    }

    BroadcastReceiver mDownloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            DownloadInfo info = (DownloadInfo) intent.getSerializableExtra("info");
            if(ACTION_COMPLETE.equals(action)){
                mProgressBar.setProgress(0);
                Toast.makeText(context, info.getFileName()+"下载完成", Toast.LENGTH_SHORT).show();
            }else if(ACTION_PROGRESS.equals(action)){
                mProgressBar.setProgress((int) info.getProgress());
                mProgressBar.setMax((int) info.getLength());
            }
        }
    };

    public void onClickStart(View view) {
        //发送开始下载的命令
        Intent intent = new Intent(MainActivity.this,DownloadService.class);
        intent.putExtra("cmd",DownloadService.CMD_START);
        intent.putExtra("url",FILE_URL);
        intent.putExtra("fileName",FILE_NAME);
        startService(intent);
    }

    public void onClickStop(View view) {
        //发送停止下载的命令
        Intent intent = new Intent(MainActivity.this,DownloadService.class);
        intent.putExtra("cmd",DownloadService.CMD_STOP);
        intent.putExtra("url",FILE_URL);
        startService(intent);
    }
}
