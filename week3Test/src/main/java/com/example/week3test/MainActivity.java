package com.example.week3test;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;

import com.example.week3test.adapter.NewsAdapter;
import com.example.week3test.databasehelp.MyDB;
import com.example.week3test.entity.NewGame;
import com.example.week3test.json.JSONLoader;
import com.example.week3test.json.JSONLoader.OnJSONLoadListener;


public class MainActivity extends Activity {

	private static final String jsomUrl = "http://www.1688wan.com/majax.action?method=getWeekll&pageNo=0";
	private ListView mNewsLv;
	private NewsAdapter adapter;
	private List<NewGame> mNewList;
	private JSONLoader mLoadJson;
	private MyDB mDBHelp;
	private static final String USER_TABLE = "game";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initData(String jsonData) {
		JSONObject obj;
		try{
			SQLiteDatabase db = mDBHelp.getWritableDatabase();
			obj = new JSONObject(jsonData);
			JSONArray arr = obj.getJSONArray("list");
			int len = arr.length();
			if(len > 0){
				//清空数据库中已有的数据
				db.execSQL("delete from "+USER_TABLE);
			}
			mNewList.clear();
			for(int i=0;i<len;i++){
				JSONObject obj2 = arr.getJSONObject(i);
				String name = obj2.getString("name");
				String iconurl = "http://www.1688wan.com"+obj2.getString("iconurl");
				String authorimg = "http://www.1688wan.com"+obj2.getString("authorimg");
				long id =  obj2.getLong("id");
				NewGame game = new NewGame(authorimg, iconurl, name);
				mNewList.add(game);
				//把下载的信息存入数据库
				ContentValues values = new ContentValues();
				values.put("iconurl", iconurl);
				values.put("name", name);
				values.put("authorimg", authorimg);
				values.put("game_id", id);
				db.insert(USER_TABLE, null, values);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} 
		adapter.notifyDataSetChanged();
	}

	@TargetApi(Build.VERSION_CODES.KITKAT) 
	private void initView() {
		mDBHelp = new MyDB(this);
		mNewsLv = (ListView)findViewById(R.id.new_game_lv);
		mNewList = new ArrayList<>();
		adapter = new NewsAdapter(this,mNewList);
		mNewsLv.setAdapter(adapter);
		
		
		mLoadJson = new JSONLoader();
		//异步任务加载数据进入数据库，
		mLoadJson.loadJSON(jsomUrl, new OnJSONLoadListener() {
			
			@Override
			public void onJSONLoadComplete(String json) {
				initData(json);
			}
		});
		//从数据库中获取数据
		try(
				SQLiteDatabase db = mDBHelp.getReadableDatabase();
				Cursor cursor = db.query(USER_TABLE, new String []{"iconurl","name","authorimg",}, null, null, null, null, null);
				){
			while(cursor.moveToNext()){
				String iconurl = cursor.getString(cursor.getColumnIndex("iconurl"));
				String name = cursor.getString(cursor.getColumnIndex("name"));
				String authorimg = cursor.getString(cursor.getColumnIndex("authorimg"));
				NewGame game = new NewGame(authorimg, iconurl, name);
				mNewList.add(game);
			}
			cursor.close();
			adapter.notifyDataSetChanged();
		}
	}
}
