package com.qianfeng.day29_download_continue.services;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.qianfeng.day29_download_continue.MainActivity;
import com.qianfeng.day29_download_continue.dao.DownloadDAO;
import com.qianfeng.day29_download_continue.dao.DownloadDAOImpl;
import com.qianfeng.day29_download_continue.entities.DownloadInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 下载服务
 * Created by xray on 17/1/12.
 */

public class DownloadService extends Service {

    public static final String TAG = "xxx";

    //下载目录
    public static final String DOWNLOAD_DIR = Environment.getExternalStorageDirectory()
            + File.separator +"downloads";

    //开始命令
    public static final String CMD_START = "CMD_START";
    //停止命令
    public static final String CMD_STOP = "CMD_STOP";
    //下载数据访问接口
    private DownloadDAO mDao;
    //线程池
    private ExecutorService mExecutorService;
    //下载线程的集合
    private Map<String,DownloadTask> mTasks;
    private LocalBroadcastManager mLbm;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDao = new DownloadDAOImpl(this);
        //初始化线程池
        mExecutorService = Executors.newCachedThreadPool();
        //初始化线程的集合
        mTasks = new HashMap<>();
        //初始化本地广播
        mLbm = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //获得Activity传来的命令
        String cmd = intent.getStringExtra("cmd");
        String url = intent.getStringExtra("url");
        String fileName = intent.getStringExtra("fileName");
        //判断命令类型
        if(CMD_START.equals(cmd)){
            //接收到开始下载的命令
            //从集合中获得下载任务
            DownloadTask task = mTasks.get(url);
            //如果任务已经启动，就跳过
            if(task != null && !task.isStop()){
                return START_NOT_STICKY;
            }
            //查询下载信息
            DownloadInfo info = mDao.query(url);
            if(info == null){
                //没有查询到下载信息
                //创建新下载信息
                info = new DownloadInfo(0,fileName,url,0,0);
                //保存到数据库中
                mDao.save(info);
            }
            //创建一个下载任务
            task = new DownloadTask(info);
            //启动一个下载任务
            mExecutorService.execute(task);
            //添加到集合中
            mTasks.put(url,task);
        }else if(CMD_STOP.equals(cmd)){
            //从集合中获得下载任务
            DownloadTask task = mTasks.get(url);
            //停止下载,有可能用户先点击停止下载，
            if(task != null){
                task.stop();
            }
        }
        return START_NOT_STICKY;
    }

    /**
     * 下载线程
     */
    class DownloadTask implements Runnable{

        private DownloadInfo downloadInfo; //下载信息
        private boolean stop = false;   //控制线程的停止

        public DownloadTask(DownloadInfo downloadInfo) {
            this.downloadInfo = downloadInfo;
        }

        //停止线程
        public void stop(){
            stop = true;
        }

        public boolean isStop(){
            return stop;
        }

        @Override
        public void run() {
            stop = false;//?
            //新建下载目录
            File dir = new File(DOWNLOAD_DIR);
            if(!dir.exists()){
                dir.mkdir();
            }
            File file = new File(dir,downloadInfo.getFileName());
            RandomAccessFile raf = null;
            try {
                //创建RandomAccessFile对象
                raf = new RandomAccessFile(file,"rwd");
                //进行联网
                String url1 = downloadInfo.getUrl();
                URL url = new URL(url1);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                //he默认情况下是get请求
                connection.setRequestMethod("GET");
                //如果是第一次下载，需要设置文件长度
                if(downloadInfo.getLength() == 0 && downloadInfo.getProgress() == 0){
                    long length = connection.getContentLength();
                    //设置文件长度
                    raf.setLength(length);
                    //更新数据库中文件长度
                    mDao.updateLength(url1,length);
                    //在更新数据库之前这个对象就已近拿出来了，所以要更新
                    downloadInfo.setLength(length);
                }else{
                    //不是第一次下载
                    //设置下载文件的范围
                    connection.setRequestProperty("Range",downloadInfo.getProgress()+"-"+downloadInfo.getLength());
                    //设置本地文件的写入位置
                    raf.seek(downloadInfo.getProgress());
                }
                Log.i(TAG, "run: "+downloadInfo);
                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK ||
                        connection.getResponseCode() == HttpURLConnection.HTTP_PARTIAL){
                    InputStream inputStream = connection.getInputStream();
                    int len = 0;
                    byte[] buffer = new byte[1024];
                    long total = downloadInfo.getProgress(); // 下载的进度
                    long start = System.currentTimeMillis(); // 开始时间
                    Intent intent = new Intent();
                    while((len = inputStream.read(buffer)) != -1){
                        raf.write(buffer,0,len);
                        total += len;
                        long end = System.currentTimeMillis(); // 结束时间
                        if(end - start > 500) {
                            start = end;
                            //保存进度到数据库中
                            mDao.updateProgress(url1, total);
                            //将进度用广播发给Activity
                            intent.setAction(MainActivity.ACTION_PROGRESS);
                            downloadInfo.setProgress(total);
                            intent.putExtra("info",downloadInfo);
                            mLbm.sendBroadcast(intent);
                            //Log.i(TAG, "sendbroadcast: "+downloadInfo);
                        }
                        //停止下载
                        if(stop){
                            break;
                        }
                    }
                    //如果是正常下载完成
                    if(!stop){
                        //删除数据库中的记录
                        mDao.delete(url1);
                        //从集合中删除下载任务
                        mTasks.remove(url1);
                        //发送下载完成的广播
                        intent.setAction(MainActivity.ACTION_COMPLETE);
                        intent.putExtra("info",downloadInfo);
                        mLbm.sendBroadcast(intent);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
