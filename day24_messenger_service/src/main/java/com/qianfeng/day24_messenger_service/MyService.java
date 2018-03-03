package com.qianfeng.day24_messenger_service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by xray on 17/1/6.
 *1、在进程B的Service中创建Handler
 2、用Messenger包装Handler
 3、在Service中的onBind方法中返回Messenger的Binder对象
 */

public class MyService extends Service {

    private Messenger outMessenger = null;

    @Override
    public void onCreate() {
        super.onCreate();
        //创建一个Messenger，用于将Handler传递给客户端
        outMessenger = new Messenger(mHandler);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return outMessenger.getBinder();
    }

    //1、在进程B的Service中创建Handler
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 999:
                    String text = msg.getData().getString("msg");
                    Log.i("xxx", "服务端收到客户端的消息: "+text);
                    //给客户端回信
                    msg.getData().putString("msg","Hello,我是服务端我收到了你的信，谢谢！");
                    try {
                        msg.replyTo.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
}
