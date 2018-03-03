package day23downappbyservice.day24messagerservice;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by hezijie on 2017/1/6.
 */
public class MessengerService extends Service {
    private Messenger messenger;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        messenger = new Messenger(mHandler);
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    //接受来自客户端的消息
                    String message = msg.getData().getString("message");
                    Log.e("111", "handleMessage: "+message );
                    //给客户端回信，直接利用对面发送过来的msg里面已经绑定了bandle不用在绑定，只需要设置回信；
                    Bundle bundle = msg.getData();
                    bundle.putString("reply","这是服务器发给客户的");
                    Messenger replyTo = msg.replyTo;
                    try {
                        replyTo.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
}
