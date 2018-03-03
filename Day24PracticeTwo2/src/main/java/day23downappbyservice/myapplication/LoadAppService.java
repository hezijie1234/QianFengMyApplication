package day23downappbyservice.myapplication;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by hezijie on 2017/1/5.
 */
public class LoadAppService extends Service {
    private LocalBroadcastManager mLocalBM;
    public static final String LOAD_ACTION = "loadApp";
    public static final String FILE_DIR = Environment.getExternalStorageDirectory()+File.separator+"download";
    private String filePath;
    private  LoadApp loadTask;
    @Override
    public void onCreate() {
        super.onCreate();
        mLocalBM = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String appPath = intent.getStringExtra("appPath");
        String appName = intent.getStringExtra("appName");
        if(null==loadTask){
            loadTask = new LoadApp();
            loadTask.execute(appPath,appName);
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    class LoadApp extends AsyncTask<String,Integer,File>{

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Intent intent = new Intent(MainActivity.LOAD_ACTION);
            intent.putExtra("progress",values[0]);
            intent.putExtra("filepath",filePath);
            //将下载进度发送给MainActivity中的接收器，发送回去的还有文件路径；
            mLocalBM.sendBroadcast(intent);
        }

        @Override
        protected File doInBackground(String... params) {
            File fileDir = new File(FILE_DIR);
            //判断文件路径是否存在
            if(!fileDir.exists()){
                fileDir.mkdir();
            }
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                if(conn.getResponseCode()==200){
                    InputStream inputStream = conn.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(inputStream);
                    File file = new File(fileDir,params[1]);
                    //在这里获取文件的存储路径，
                    filePath = file.getAbsolutePath();
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                    int len = 0;
                    int count = 0;
                    int contentLength = conn.getContentLength();
                    byte[] bytes = new byte[1024];
                    while((len = bis.read(bytes))!=-1){
                        bos.write(bytes,0,len);
                        count+=len;
                        //将更新进度发送给OnprogressUpdate
                        publishProgress((int)((double)count/contentLength * 100));
                    }
                    bos.flush();
                    bos.close();
                    return file;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(File file) {
            loadTask =null;
            super.onPostExecute(file);
        }
    }
}
