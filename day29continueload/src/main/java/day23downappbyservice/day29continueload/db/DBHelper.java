package day23downappbyservice.day29continueload.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hezijie on 2017/1/12.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "download.db";
    public static final int VERSION = 1;
    public static final String TABLE_NAME = "load";
    public static final String CREATE_TABLE = "create table "+TABLE_NAME+"(" +
            "_id integer primary key autoincrement," +
            "fileName text," +
            "url text," +
            "progress integer," +
            "length integer);";
    public DBHelper(Context context){
        this(context,DB_NAME,null,VERSION);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
