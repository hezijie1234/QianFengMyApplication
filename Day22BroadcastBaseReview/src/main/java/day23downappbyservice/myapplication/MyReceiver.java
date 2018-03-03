package day23downappbyservice.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by hezijie on 2017/1/5.
 */
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(MainActivity.BROAD_ACTION.equals(intent.getAction())){
            Toast.makeText(context,intent.getStringExtra("000"),Toast.LENGTH_SHORT).show();
        }
    }
}
