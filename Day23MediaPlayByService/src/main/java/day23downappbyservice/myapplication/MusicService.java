package day23downappbyservice.myapplication;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

/**
 * Created by hezijie on 2017/1/5.
 */
public class MusicService extends Service {
    private MediaPlayer mMediaPlay;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("111", "onDestroy: " );
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        //返回true表示可以重新绑定
        return true;
    }

    class MyBind extends Binder{
        public MusicService getService(){
            return  MusicService.this;
        }
    }

    public void play(String filePath){
        if(mMediaPlay==null){
            mMediaPlay = new MediaPlayer();
        }
        mMediaPlay.reset();
        try {
            mMediaPlay.setDataSource(filePath);
            mMediaPlay.prepareAsync();
            mMediaPlay.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mMediaPlay.start();
                }
            });
            mMediaPlay.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mMediaPlay.stop();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void pause(){
        if(mMediaPlay!=null){
            mMediaPlay.pause();
        }
    }

    public void stop(){
        if(mMediaPlay!=null){
            mMediaPlay.stop();
        }
    }
    public void start(){
        if(mMediaPlay!=null){
            mMediaPlay.start();
        }
    }
}
