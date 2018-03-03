package com.example.week3test.databasehelp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDB extends SQLiteOpenHelper{

	private static final int MYDB_VERSION = 2;
	private static final String MYDB_NAME = "game.db";
	private static final String USER_TABLE = "game";
	private static final String CREATE_TABLE = "create table "+USER_TABLE+"(" +
			"id integer primary key autoincrement," +
			"iconurl text," +
			"name text," +
			"authorimg text," +
			"game_id Long)";
	private static final String DROP_TABLE = "drop table if exists "+USER_TABLE; 
	
	
	public MyDB(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	
	public MyDB(Context context){
		this(context,MYDB_NAME,null,MYDB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
		Log.e("111", "onCreate");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DROP_TABLE);
		Log.e("111", "onUpgrade");
		onCreate(db);
	}

}
