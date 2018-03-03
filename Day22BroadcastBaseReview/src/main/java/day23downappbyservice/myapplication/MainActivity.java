package day23downappbyservice.myapplication;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final String BROAD_ACTION = "this is a broadcast flag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //使用动态注册来注册四大组件之一的广播
//        IntentFilter filter = new IntentFilter(BROAD_ACTION);
//        registerReceiver(new MyReceiver(),filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在结束activity的时候解除广播
//        unregisterReceiver(new MyReceiver());
    }

    public void broadOnClick(View view) {
        Intent intent = new Intent(BROAD_ACTION);
        intent.putExtra("000","发送广播了");
        sendBroadcast(intent);
    }
}
