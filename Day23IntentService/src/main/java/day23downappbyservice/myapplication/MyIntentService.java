package day23downappbyservice.myapplication;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by hezijie on 2017/1/5.
 */
public class MyIntentService extends IntentService{
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService(String name) {
        super("MyIntentWorkThread");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        //在这个方法中可以执行多个耗时的操作
    }
}
