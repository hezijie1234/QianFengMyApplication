package day23downappbyservice.day29continueload.services;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import day23downappbyservice.day29continueload.DownloadDAOImpl;
import day23downappbyservice.day29continueload.entities.DownLoadInfo;
import day23downappbyservice.day29continueload.utils.Constants;

/**
 * Created by hezijie on 2017/1/16.
 */
public class LoadAppService  extends Service{
    private static final String TAG = "111";
    //本地广播
    private LocalBroadcastManager mLocalMana;
    //线程池
    private ExecutorService mExecutor;
    //放置下载任务的地方；
    private Map<String ,LoadApp> mLoadMap;
    //数据库的访问接口；
    private DownloadDAOImpl mDownImpl;
    private String mFileName;
    //文件下载目录
    public static final String FILE_DIR = Environment.getExternalStorageDirectory()+ File.separator+"AppLoad";


    @Override
    public void onCreate() {
        super.onCreate();
        mLocalMana = LocalBroadcastManager.getInstance(this);
        mExecutor =  Executors.newCachedThreadPool();
        mLoadMap = new LinkedHashMap<>();
        mDownImpl = new DownloadDAOImpl(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String cmd = intent.getStringExtra("cmd");
        String url = intent.getStringExtra("url");
        Log.e(TAG, "onStartCommand: "+cmd );
        mFileName = intent.getStringExtra("filename");
        //接受命令，判断开始还是停止
        if(Constants.LOAD_START.equals(cmd)){
            LoadApp loadApp = mLoadMap.get(url);
            //获取下载信息当下载任务不为空且正在下载时，
            if(loadApp!=null&&!loadApp.isStop()){
                return START_NOT_STICKY;
            }
            DownLoadInfo info = mDownImpl.query(url);
            //查询数据库中是否有下载信息，
            if(info==null){
                //如果没有下载信息，创建下载信息；
                info = new DownLoadInfo(url,0,mFileName,0);
                mDownImpl.add(info);
            }
            //当下载任务暂停或者还没开始的时候，就创建一个下载任务从新开始下载
            //而判定这个任务是从头开始下载还是中途开始下载有下载任务逻辑判定
            LoadApp load = new LoadApp(info);
            //开始下载任务
            mExecutor.execute(load);
            //将下载任务加入到集合中
            mLoadMap.put(url,load);
            //当接收到停止下载任务命令时 就停止下载
        }else if(Constants.LOAD_END.equals(cmd)){
            LoadApp loadApp = mLoadMap.get(url);
            if(loadApp!=null){
                loadApp.setStop(true);
            }
        }
        return START_NOT_STICKY;
    }

    class LoadApp implements Runnable{

        private boolean stop = false;
        private DownLoadInfo info;

        public LoadApp(DownLoadInfo info) {
            this.info = info;
        }

        @Override
        public void run() {
            //在任务重新开始的时候，stop为true，而任务重新开始就设置stop为false
            setStop(false);
            //在下载之前需要确定下载文件夹是否存在
            File file = new File(FILE_DIR);
            if(!file.exists()){
                file.mkdir();
            }
            File file1 = new File(file,info.getFileName());
            //创建随机读取文件
            Log.e(TAG, "run: "+"进入下载任务" );
            try {
                RandomAccessFile raf = new RandomAccessFile(file1,"rwd");
                URL url = new URL(info.getUrl());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                //如果是第一次下载就在数据库中存储信息
                if(info.getProgress() == 0 && info.getLength() == 0){
                    long length = conn.getContentLength();
                    info.setLength(length);
                    //更新数据库中的信息
                    mDownImpl.updateLength(info.getUrl(),length);
                    //设置下载文件长度
                    raf.setLength(length);
                }else{
                    conn.setRequestProperty("Range",info.getProgress()+"-"+info.getLength());
                    //如果是从新开始就需要设置下载文件的写入位置
                    raf.seek(info.getProgress());
                }
                Log.e(TAG, "run: "+info );
                if(conn.getResponseCode() == HttpURLConnection.HTTP_OK || conn.getResponseCode() == HttpURLConnection.HTTP_PARTIAL){
                    InputStream inputStream = conn.getInputStream();
                    int len = 0;
                    byte[] bytes = new byte[1024];
                    long startTime = System.currentTimeMillis();
                    long total = info.getProgress();
                    Intent intent = new Intent();
                    while((len=inputStream.read(bytes))!=-1){
                        raf.write(bytes,0,len);
                        total+=len;
                        long endTime = System.currentTimeMillis();
                        //没0.5秒发送一次进度广播，并且把数据保存到数据库
                        if(info.getProgress()==info.getLength()){
                            intent.setAction(Constants.LOAD_PROGRESS);
                            info.setProgress(total);
                            intent.putExtra("info",info);
                            mLocalMana.sendBroadcast(intent);
                            break;
                        }
                        if(endTime-startTime>=500){
                            startTime = endTime;
                            mDownImpl.updateProgress(info.getUrl(),total);
                            intent.setAction(Constants.LOAD_PROGRESS);
                            info.setProgress(total);
                            intent.putExtra("info",info);
                            mLocalMana.sendBroadcast(intent);
                        }
                        //当发现stop值编程true时就说明用户点击了暂停，要文件的写入
                        if(isStop()){
                            break;
                        }
                    }
                    //当点击暂停的时候就关闭流资源；
                    inputStream.close();
                    //当正常下载完成的时候，stop为false
                    if(!isStop()){
                        Log.e(TAG, "run: "+"下载完成" );
                        //stop=true;
                        intent.setAction(Constants.LOAD_FINISH);
                        intent.putExtra("info",info);
                        mLocalMana.sendBroadcast(intent);
                        mDownImpl.delete(info.getUrl());
                        mLoadMap.remove(info.getUrl());
                        // 关闭数据库
                        mDownImpl.close();
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

        public void setStop(boolean stop){
            this.stop = stop;
        }

        public boolean isStop(){
            return stop;
        }
    }
}
