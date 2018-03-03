package com.qianfeng.day29_download_continue.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.qianfeng.day29_download_continue.db.DBHelper;
import com.qianfeng.day29_download_continue.entities.DownloadInfo;

/**
 * 下载访问接口的实现类
 * Created by xray on 17/1/12.
 */

public class DownloadDAOImpl implements DownloadDAO{

    private SQLiteDatabase mDb;

    public DownloadDAOImpl(Context context){
        if(mDb == null){
            mDb = new DBHelper(context).getWritableDatabase();
        }
    }

    @Override
    public void save(DownloadInfo downloadInfo) {
        mDb.execSQL("insert into "+DBHelper.TB_DOWNLOAD+
                " (fileName,url,progress,length) values(?,?,?,?)",
                new Object[]{downloadInfo.getFileName(),downloadInfo.getUrl(),
                        downloadInfo.getProgress(),downloadInfo.getLength()});
    }

    @Override
    public DownloadInfo query(String url) {
        Cursor cursor = mDb.rawQuery("select * from " + DBHelper.TB_DOWNLOAD + " where url = ?",
                new String[]{url});
        if(cursor.moveToFirst()){
            DownloadInfo info = new DownloadInfo();
            info.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            info.setFileName(cursor.getString(cursor.getColumnIndex("fileName")));
            info.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            info.setProgress(cursor.getLong(cursor.getColumnIndex("progress")));
            info.setLength(cursor.getLong(cursor.getColumnIndex("length")));
            return info;
        }
        return null;
    }

    @Override
    public void updateProgress(String url, long progress) {
        mDb.execSQL("update "+DBHelper.TB_DOWNLOAD+
                        " set progress = ? where url = ?",
                new Object[]{progress , url});
    }

    @Override
    public void updateLength(String url, long length) {
        mDb.execSQL("update "+DBHelper.TB_DOWNLOAD+
                        " set length = ? where url = ?",
                new Object[]{length , url});
    }

    @Override
    public void delete(String url) {
        mDb.execSQL("delete from "+DBHelper.TB_DOWNLOAD+
                        "  where url = ?",
                new Object[]{ url});
    }

    @Override
    public void close() {
        mDb.close();
    }
}
