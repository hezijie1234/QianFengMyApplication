package day23downappbyservice.myapplication;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ListView mMusicLv;
    private MusicService mMusicService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        Intent intent = new Intent(this,MusicService.class);
        startService(intent);
        bindService(intent,conn, Service.BIND_AUTO_CREATE);
    }
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MyBind myBind = (MusicService.MyBind) service;
            mMusicService = myBind.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //当连接断开的时候就
            mMusicService = null;
        }
    };
    private void initViews() {
        mMusicLv = (ListView) findViewById(R.id.music_lv);
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media._ID, MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.DATA}
                , null, null, null);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.music_item,cursor
                ,new String []{MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.DATA},
                new int[]{R.id.singer_tv,R.id.song_tv,R.id.songpath_tv},CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        mMusicLv.setAdapter(adapter);
        mMusicLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView path = (TextView) view.findViewById(R.id.songpath_tv);
                Log.e("111", "onItemClick: " +path.getText().toString());
                mMusicService.play(path.getText().toString());
            }
        });
    }

    public void onClickPlay(View view) {
        mMusicService.start();
    }

    public void onClickPause(View view) {
        mMusicService.pause();
    }

    public void onCLickStop(View view) {
        mMusicService.stop();
    }

    @Override
    protected void onStop() {
        //当我们退出activity的时候需要对service解绑，因为activity不存在了
        if(conn!=null){
            unbindService(conn);
        }
        super.onStop();
    }
}
