package day23downappbyservice.day29continueload;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;

import day23downappbyservice.day29continueload.entities.DownLoadInfo;
import day23downappbyservice.day29continueload.services.LoadAppService;
import day23downappbyservice.day29continueload.utils.Constants;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "111";
    public LocalBroadcastManager mLoaclMan;
    private ProgressBar mProgressBar;
    private static final String LOAD_URL = "http://music.baidu.com/cms/BaiduMusic-pcwebdownload.apk";

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case Constants.LOAD_PROGRESS:
                    DownLoadInfo info = (DownLoadInfo) intent.getSerializableExtra("info");
                    Log.e(TAG, "onReceive: "+info );
                    mProgressBar.setMax((int)info.getLength());
                    mProgressBar.setProgress((int)info.getProgress());
                    break;
                case Constants.LOAD_FINISH:
                    DownLoadInfo info2 = (DownLoadInfo) intent.getSerializableExtra("info");
                    mProgressBar.setProgress((int) info2.getLength());
                    Intent intent1 = new Intent(Intent.ACTION_VIEW);
                    intent1.setDataAndType(Uri.parse("file:///"+LoadAppService.FILE_DIR+ File.separator+Constants.FILE_NAME),"application/vnd.android.package-archive");
                    startActivity(intent1);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //注册广播
        mLoaclMan = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.LOAD_PROGRESS);
        filter.addAction(Constants.LOAD_FINISH);
        mLoaclMan.registerReceiver(mReceiver,filter);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

    }

    public void onClickStart(View view) {
        Intent intent = new Intent(this,LoadAppService.class);
        intent.putExtra("cmd",Constants.LOAD_START);
        intent.putExtra("url",LOAD_URL);
        intent.putExtra("filename",Constants.FILE_NAME);
        startService(intent);
    }

    public void onClickEnd(View view) {
        Intent intent = new Intent(this,LoadAppService.class);
        intent.putExtra("cmd",Constants.LOAD_END);
        intent.putExtra("url",LOAD_URL);
        intent.putExtra("filename",Constants.FILE_NAME);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoaclMan.unregisterReceiver(mReceiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
       // unbindService();
    }
}
