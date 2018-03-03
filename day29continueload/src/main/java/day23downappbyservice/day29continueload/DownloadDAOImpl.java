package day23downappbyservice.day29continueload;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import javax.crypto.spec.DHGenParameterSpec;

import day23downappbyservice.day29continueload.DownloadDAO;
import day23downappbyservice.day29continueload.db.DBHelper;
import day23downappbyservice.day29continueload.entities.DownLoadInfo;

/**
 * Created by hezijie on 2017/1/12.
 */
public class DownloadDAOImpl implements DownloadDAO {
    private SQLiteDatabase mDB;

    public DownloadDAOImpl(Context context) {
        if(mDB==null){
            mDB = new DBHelper(context).getWritableDatabase();
        }
    }

    @Override
    public void add(DownLoadInfo info) {
        mDB.execSQL("insert into "+DBHelper.TABLE_NAME+"(fileName,url,progress,length) values" +
                "(?,?,?,?);",new Object[]{info.getFileName(),info.getUrl(),info.getProgress(),info.getLength()});
    }

    @Override
    public void delete(String url) {
        mDB.execSQL("delete from "+DBHelper.TABLE_NAME +" where url=?",new Object[]{url});
    }

    @Override
    public DownLoadInfo query(String url) {
        Cursor cursor = mDB.rawQuery("select * from " + DBHelper.TABLE_NAME + " where url=?", new String[]{url});
        if(cursor.moveToFirst()){
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String fileName = cursor.getString(cursor.getColumnIndex("fileName"));
            String url2 = cursor.getString(cursor.getColumnIndex("url"));
            long progress = cursor.getLong(cursor.getColumnIndex("progress"));
            long length = cursor.getLong(cursor.getColumnIndex("length"));
            DownLoadInfo info = new DownLoadInfo(id,url2,progress,fileName,length);
            return info;
        }
        return null;
    }

    @Override
    public void updateProgress(String url, long progress) {
        mDB.execSQL("update "+ DBHelper.TABLE_NAME+" set progress=? where url=? ",new Object[]{progress,url});
    }

    @Override
    public void updateLength(String url, long length) {
        mDB.execSQL("update "+ DBHelper.TABLE_NAME+" set length=? where url=? ",new Object[]{length,url});
    }

    @Override
    public void close() {
        mDB.close();
    }
}
