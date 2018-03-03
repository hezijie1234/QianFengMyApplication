package com.qianfeng.day24_aidl_service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.qianfeng.day24_aidl_service.aidl.IPlayer;

import java.io.IOException;

/**
 * 服务端：
 1、定义AIDL文件，定义接口
 会在build/sources/bebug/目录下生成接口文件
 2、定义Service，在Service中实现接口
 内部类Stub继承Binder
 提供asInterface方法实现Binder到接口的转换
 需要实现Stub的抽象方法
 3、在onBind方法中，将实现接口的对象返回
 * Created by xray on 17/1/6.
 */

public class MusicService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyPlayer();
    }

    /**
     * 播放器类
     * 继承AIDL接口中的Stub抽象类
     */
    class MyPlayer extends IPlayer.Stub{

        private MediaPlayer mediaPlayer;

        @Override
        public void play(String path) throws RemoteException {
            if(mediaPlayer == null){
                mediaPlayer = new MediaPlayer();
                mediaPlayer.reset();
                try {
                    mediaPlayer.setDataSource(path);
                    mediaPlayer.prepareAsync();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mediaPlayer.start();
                        }
                    });
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
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
        }

        @Override
        public void pause() throws RemoteException {
            if(mediaPlayer != null){
                mediaPlayer.pause();
            }
        }

        @Override
        public void resume() throws RemoteException {
            if(mediaPlayer != null){
                mediaPlayer.start();
            }
        }

        @Override
        public void stop() throws RemoteException {
            if(mediaPlayer != null){
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }
    }
}
