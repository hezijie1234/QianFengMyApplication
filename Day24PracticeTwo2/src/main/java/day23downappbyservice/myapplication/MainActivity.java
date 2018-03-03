package day23downappbyservice.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private LocalBroadcastManager mLocalBM;
    public static final String LOAD_ACTION = "loadApp";
    public static final String APP_PATH = "http://sj.img4399.com/game_list/47/com.wepie.snake/wepie.snake.v117518.apk?ref=news";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //注册一个本地广播
        mLocalBM = LocalBroadcastManager.getInstance(this);
        IntentFilter filter = new IntentFilter(LOAD_ACTION);
        mLocalBM.registerReceiver(receiver,filter);
        initViews();
    }

    private void initViews() {
        mProgressBar = (ProgressBar) findViewById(R.id.load_progressbar);
    }
    //接受来自service的广播
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(LOAD_ACTION.equals(intent.getAction())){
                int progress = intent.getIntExtra("progress",0);
                mProgressBar.setProgress(progress);
                String filePath = intent.getStringExtra("filepath");
                //当progress==100的时候说明文件下载完成，可以开始安装了；
                if(progress==100){
                    Intent intentPlay = new Intent(Intent.ACTION_VIEW);
                    intentPlay.setDataAndType(Uri.parse("file://"+ filePath),"application/vnd.android.package-archive");
                    startActivity(intentPlay);
                }
            }
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocalBM.unregisterReceiver(receiver);
    }

    public void downClick(View view) {
        //使用app之间的Service一般使用startService来开启服务
        Intent intent = new Intent(this,LoadAppService.class);
        intent.putExtra("appPath",APP_PATH);
        intent.putExtra("appName","snake.apk");
        startService(intent);
    }
}
