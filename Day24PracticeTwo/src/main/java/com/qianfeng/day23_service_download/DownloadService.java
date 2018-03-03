package com.qianfeng.day23_service_download;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 文件下载服务
 * Created by xray on 17/1/5.
 */

public class DownloadService extends Service{

    public static final String DOWN_DIR =
            Environment.getExternalStorageDirectory()
                    + File.separator +"downloads";
    private LocalBroadcastManager mLbm;
    private DownloadTask mTask;

    @Override
    public void onCreate() {
        super.onCreate();
        mLbm = LocalBroadcastManager.getInstance(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //获得下载文件名
        String file_path = intent.getStringExtra("file_path");
        String file_name = intent.getStringExtra("file_name");
        Log.i("xxx", "onStartCommand: "+file_path);
        if(!TextUtils.isEmpty(file_path) && !TextUtils.isEmpty(file_name)){
            //执行下载任务
           if(mTask == null){
               mTask = new DownloadTask();
               mTask.execute(file_path,file_name);
           }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 下载任务
     * @author xray
     *
     */
    class DownloadTask extends AsyncTask<String,Integer,File> {

        @Override
        protected File doInBackground(String... params) {
            //创建下载目录 http:....../aaa.apk
            File dir = new File(DOWN_DIR);
            if(!dir.exists()){
                dir.mkdir();
            }
            //截取出文件名
            String filename = params[1];
            InputStream inputStream = null;
            OutputStream output = null;
            try {
                URL url = new URL(params[0]);
                //打开连接
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                //设置连接超时
                conn.setConnectTimeout(5000);
                //判断连接状态
                if(conn.getResponseCode() == 200){
                    inputStream = conn.getInputStream();
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    //创建下载文件
                    File file = new File(dir,filename);
                    //创建输出流
                    output = new FileOutputStream(file);
                    //获得文件长度
                    int length = conn.getContentLength();
                    //总进度
                    int total = 0;
                    //读取输入流中的数据
                    while((len = inputStream.read(buffer)) != -1){
                        //写入到sdcard文件中
                        output.write(buffer, 0, len);
                        //累加进度,发送进度
                        total += len;
                        publishProgress((int)(((float)total / length) * 100));
                    }
                    return file;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                try{
                    if(inputStream != null){
                        inputStream.close();
                    }
                    if(output != null){
                        output.close();
                    }
                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //发送广播，更新进度
            Intent intent = new Intent(MainActivity.ACTION_PROGRESS);
            intent.putExtra("progress",values[0]);
            mLbm.sendBroadcast(intent);
        }

        @Override
        protected void onPostExecute(File result) {
            //发送广播，完成下载
            Intent intent = new Intent(MainActivity.ACTION_FINISH);
            intent.putExtra("file_path",result.getAbsolutePath());
            mLbm.sendBroadcast(intent);
            mTask = null;
        }

    }
}
