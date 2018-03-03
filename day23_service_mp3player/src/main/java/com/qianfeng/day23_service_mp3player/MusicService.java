package com.qianfeng.day23_service_mp3player;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.IOException;

/**
 * 音乐播放的服务
 * Created by xray on 17/1/5.
 */

public class MusicService extends Service {

    private MediaPlayer mediaPlayer = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //当客户端绑定时，返回Binder对象
        return new MyBinder();
    }

    /**
     * 此Binder对象作为组件之间的桥梁，将当前的Service对象进行传送
     */
    class MyBinder extends Binder {
        public MusicService getService(){
            return MusicService.this;
        }
    }

    public void play(String path){
        if(mediaPlayer == null){
            mediaPlayer = new MediaPlayer();
            //重置播放器
            mediaPlayer.reset();
            //设置播放文件
            try {
                mediaPlayer.setDataSource(path);
                //缓存文件,异步
                mediaPlayer.prepareAsync();
                //添加缓冲监听
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        //播放
                        mediaPlayer.start();
                    }
                });
                //添加播放完成监听
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stop();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void pause(){
        if(mediaPlayer != null){
            mediaPlayer.pause();
        }
    }

    public void resume(){
        if(mediaPlayer != null){
            mediaPlayer.start();
        }
    }

    public void stop(){
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
