package com.qianfeng.day24_messenger_client;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Date;

/**
 * 4、在进程A中的Activity绑定进程B中的Service
 5、在ServiceConnection中通过Binder获得Messenger对象
 6、通过Messenger对象发送消息
 */
public class MainActivity extends AppCompatActivity {

    private Messenger mServiceMessenger;
    private Messenger mMyMessenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //绑定服务端
        Intent intent = new Intent("MyService");
        intent.setPackage("com.qianfeng.day24_messenger_service");
        bindService(intent,connection, Service.BIND_AUTO_CREATE);
        //使用Messenger包装自己的Handler
        mMyMessenger = new Messenger(mHandler);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //获得服务端的Messenger
            mServiceMessenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void onClickSendMessage(View view) {
        //发送消息给服务端的Handler
        Message msg = new Message();
        msg.what = 999;
        Bundle bundle = new Bundle();
        bundle.putString("msg","Hello！我是客户端 "+(new Date().toLocaleString()));
        msg.setData(bundle);
        //发消息时，设置回信的信使
        msg.replyTo = mMyMessenger;
        try {
            mServiceMessenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 此Handler接收服务器端的消息
     */
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 999:
                    String text = msg.getData().getString("msg");
                    Log.i("xxx", "客户端收到服务器端的消息: "+text);
                    break;
            }
        }
    };
}
