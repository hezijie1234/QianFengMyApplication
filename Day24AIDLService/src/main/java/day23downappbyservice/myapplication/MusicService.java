package day23downappbyservice.myapplication;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

import day23downappbyservice.aidl.IMusicService;

/**
 * Created by hezijie on 2017/1/6.
 */
public class MusicService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyMusicPlay();
    }

    class MyMusicPlay extends IMusicService.Stub{
        private MediaPlayer mMediaPlayer;
        @Override
        public void play(String str) throws RemoteException {
            Log.e("111", "play: "+str );
            if(null==mMediaPlayer){
                mMediaPlayer = new MediaPlayer();
                Log.e("111", "play: 创建MediaPlayer" );
            }
            mMediaPlayer.reset();
            try {
                mMediaPlayer.setDataSource(str);
                mMediaPlayer.prepareAsync();
                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mMediaPlayer.start();
                    }
                });
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        try {
                            stop();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void pause() throws RemoteException {
            if(null!=mMediaPlayer){
                mMediaPlayer.pause();
            }
        }

        @Override
        public void resume() throws RemoteException {
            if(null!=mMediaPlayer){
                mMediaPlayer.start();
            }
        }

        @Override
        public void stop() throws RemoteException {
            if(null!=mMediaPlayer){
                mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = null;
            }
        }
    }
}
