package com.qianfeng.day29_download_continue.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库帮助类
 * Created by xray on 17/1/12.
 */

public class DBHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "download.db";
    public static final int VERSION = 1;
    public static final String TB_DOWNLOAD = "tb_download";
    public static final String CREAET_TB_DOWNLOAD = "create table " + TB_DOWNLOAD +
            " (_id integer primary key autoincrement," +
            "fileName text," +
            "url text," +
            "progress integer," +
            "length integer)";

    public DBHelper(Context context){
        this(context, DB_NAME ,null , VERSION);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAET_TB_DOWNLOAD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
