package com.hezijie.day13mydb;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

	private Button mInsertBtn;
	private Button mDeleteBtn;
	private Button mSelectBtn;
	private Button mUpdateBtn;

	private static final String USER_TABLE = "student";
	private MyDB database;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		listener();
	}

	private void initView() {
		database = new MyDB(this);
		mInsertBtn = (Button)findViewById(R.id.insert_data_btn);
		mDeleteBtn = (Button)findViewById(R.id.delete_data_btn);
		mSelectBtn = (Button)findViewById(R.id.select_data_btn);
		mUpdateBtn = (Button)findViewById(R.id.update_data_btn);
	}

	@TargetApi(Build.VERSION_CODES.KITKAT) 
	private void listener() {
		mInsertBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try(
						SQLiteDatabase db = database.getWritableDatabase();
						){
					String insert = "insert into "+USER_TABLE+"(username,password) values(?,?);";
					long i = System.currentTimeMillis() % 100;
					db.execSQL(insert,new Object[]{"admin"+i,"123"+i});
				}
			}
		});
		mDeleteBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try(
						SQLiteDatabase db = database.getWritableDatabase();
						){
					String delete = "delete from "+USER_TABLE+" where id=?;";
					db.execSQL(delete,new Object[]{3});
				}
			}
		});
		mUpdateBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try(
						SQLiteDatabase db = database.getWritableDatabase();
						){
					String delete = "update "+USER_TABLE+" set username=?,password=? where id=?;";
					long i = System.currentTimeMillis() % 100;
					db.execSQL(delete,new Object[]{"admin"+i,"123"+i,"1"});
				}
			}
		});
		mSelectBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String select = "select * from "+USER_TABLE;
				try(
						SQLiteDatabase db = database.getWritableDatabase();
						Cursor cursor = db.rawQuery(select, null);
						){
					while(cursor.moveToNext()){
						int id = cursor.getInt(cursor.getColumnIndex("id"));
						String username = cursor.getString(cursor.getColumnIndex("username"));
						String password = cursor.getString(cursor.getColumnIndex("password"));
						Log.e("111", "id"+id+"----"+username+"--"+password);
					}
				}
			}
		});
	}

	@TargetApi(Build.VERSION_CODES.KITKAT) 
	public void insertWithout(View view){
		try(
				SQLiteDatabase db = database.getWritableDatabase();
				){
			ContentValues values = new ContentValues();
			long i = System.currentTimeMillis() % 100;
			values.put("username", "admin"+i);
			values.put("password", "123"+i);
			db.insert(USER_TABLE, null, values);
		}
	}

	@TargetApi(Build.VERSION_CODES.KITKAT) 
	public void deleteWithout(View view){
		try(
				SQLiteDatabase db = database.getWritableDatabase();
				){
			//			String delete = "delete from "+USER_TABLE+" where id=?;";
			//			db.execSQL(delete,new Object[]{3});
			db.delete(USER_TABLE, "id=?",new String[]{"1"} );
		}
	}
	@TargetApi(Build.VERSION_CODES.KITKAT) 
	public void updateWithout(View view){
		try(
				SQLiteDatabase db = database.getWritableDatabase();
				){
			//			String delete = "update "+USER_TABLE+" set username=?,password=? where id=?;";
			long i = System.currentTimeMillis() % 100;
			//			db.execSQL(delete,new Object[]{"admin"+i,"123"+i,"1"});
			ContentValues values = new ContentValues();
			values.put("username", "admin"+i);
			values.put("password", "123"+i);
			db.update(USER_TABLE, values, "id=?", new String[]{"2"});
		}
	}
	@TargetApi(Build.VERSION_CODES.KITKAT) 
	public void selectWithout(View view){
		try(
				SQLiteDatabase db = database.getWritableDatabase();
				Cursor cursor = db.query(USER_TABLE, new String[]{"id","username",
				"password"}, null, null, null, null, "id desc");
				){
			while(cursor.moveToNext()){
				int id = cursor.getInt(cursor.getColumnIndex("id"));
				String username = cursor.getString(cursor.getColumnIndex("username"));
				String password = cursor.getString(cursor.getColumnIndex("password"));
				Log.e("111", "id"+id+"----"+username+"--"+password);
			}
		}
	}


}
