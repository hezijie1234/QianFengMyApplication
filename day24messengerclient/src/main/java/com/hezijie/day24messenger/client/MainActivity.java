package com.hezijie.day24messenger.client;

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

public class MainActivity extends AppCompatActivity {
    private Messenger serverMessenger;
    private Messenger mClientMess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent("this_is_a_message");
        intent.setPackage("day23downappbyservice.day24messagerservice");
        bindService(intent,conn, Service.BIND_AUTO_CREATE);
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            serverMessenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serverMessenger = null;
        }
    };
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Log.e("111", "handleMessage: "+msg.getData().getString("reply") );
                    break;
            }
        }
    };

    public void sendOnClick(View view) {
        Message msg = new Message();
        msg.what=1;
        Bundle bundle = new Bundle();
        bundle.putString("message","这是客户端发送给服务器端的消息");
        msg.setData(bundle);
        mClientMess = new Messenger(handler);
        msg.replyTo = mClientMess;
        try {
            if(serverMessenger!=null){
                serverMessenger.send(msg);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
