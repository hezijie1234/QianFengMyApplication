package com.qianfeng.day23_service_mp3player;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ListView mMp3Lv = null;
    private MusicService musicService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //绑定播放服务
        Intent intent = new Intent(this,MusicService.class);
        bindService(intent,connection, Service.BIND_AUTO_CREATE);
        initViews();
    }

    /**
     * 连接对象
     */
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //当连接上个Service后，将Binder对象中的Service取出
            MusicService.MyBinder binder = (MusicService.MyBinder) service;
            musicService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicService = null;
        }
    };

    private void initViews() {
        mMp3Lv = (ListView) findViewById(R.id.mp3_lv);
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.DATA},
                null, null, null);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.mp3_item,
                cursor,
                new String[]{ MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.DATA},
                new int[]{R.id.song_tv,R.id.singer_tv,R.id.path_tv},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        mMp3Lv.setAdapter(adapter);

        mMp3Lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.path_tv);
                String path = textView.getText().toString();
                //播放音乐
                musicService.play(path);
            }
        });

    }

    public void onClickResume(View view) {
        musicService.resume();

    }

    public void onClickPause(View view) {
        musicService.pause();
    }

    public void onClickStop(View view) {
        musicService.stop();
    }
}
