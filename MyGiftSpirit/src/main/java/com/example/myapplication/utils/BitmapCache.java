package com.example.myapplication.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.util.LruCache;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**通过内存缓存和磁盘缓存，来存储图片
 * Created by hezijie on 2017/1/3.
 */
public class BitmapCache {
    private static int MEMORY_SIZE = 1024*1024*4;
    private static LruCache<String,Bitmap> memoryCache;

    private static int DISK_SIZE = MEMORY_SIZE*4;
    private static DiskLruCache diskLruCache;

    public static void initCache(Context context){
        if(memoryCache==null){
            memoryCache = new LruCache<>(MEMORY_SIZE);
        }
        if(diskLruCache==null){
            try {
                diskLruCache = DiskLruCache.open(getDiskFile(context),getAppVersion(context),1,DISK_SIZE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public static void save2Disk(String urlStr,Bitmap bitmap){
        try {
            DiskLruCache.Editor editor = diskLruCache.edit(md5(urlStr));
            if(editor!=null){
                OutputStream outputStream = editor.newOutputStream(0);
                boolean compress = bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                if(compress){
                    editor.commit();
                }else{
                    editor.abort();
                }
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Bitmap getFromDisk(String urlStr){
        try {
            DiskLruCache.Snapshot snapshot = diskLruCache.get(md5(urlStr));
            if(snapshot!=null){
                InputStream inputStream = snapshot.getInputStream(0);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static int getAppVersion(Context context){
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(),0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static File getDiskFile(Context context){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return context.getExternalCacheDir();
        }
            return context.getCacheDir();
    }

    public static void save2Memory(String str,Bitmap bitmap){
        memoryCache.put(md5(str),bitmap);
    }
    public static Bitmap getFromMemory(String urlStr){
        return memoryCache.get(md5(urlStr));
    }
    public static String md5(String str){
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            md5.update(str.getBytes());
            byte[] digest = md5.digest();
            int len = digest.length;
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<len;i++){
                sb.append(Integer.toHexString(Math.abs(digest[i])));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
