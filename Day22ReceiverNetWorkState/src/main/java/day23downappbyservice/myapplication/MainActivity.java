package day23downappbyservice.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ConnectivityManager mConn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mConn = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            NetworkInfo.State mobileState = mConn.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
            NetworkInfo.State wifiState = mConn.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
            if(mobileState != null&&wifiState != null && NetworkInfo.State.CONNECTED!=wifiState&& NetworkInfo.State.CONNECTED==mobileState){
                Toast.makeText(context,"移动网络连接",Toast.LENGTH_SHORT).show();
            }else if(mobileState != null&&wifiState != null && NetworkInfo.State.CONNECTED==wifiState&& NetworkInfo.State.CONNECTED!=mobileState){
                Toast.makeText(context,"wifi网络连接",Toast.LENGTH_SHORT).show();
            }else if(mobileState != null&&wifiState != null && NetworkInfo.State.CONNECTED!=wifiState&& NetworkInfo.State.CONNECTED!=mobileState){
                Toast.makeText(context,"没有网络",Toast.LENGTH_SHORT).show();

            }
        }
    };
}
