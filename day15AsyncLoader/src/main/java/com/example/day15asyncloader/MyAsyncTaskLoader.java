package com.example.day15asyncloader;

import java.util.ArrayList;
import java.util.List;

import android.content.AsyncTaskLoader;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;

public class MyAsyncTaskLoader extends AsyncTaskLoader<List<MyContacts>>{

	private Context context;
	
	public MyAsyncTaskLoader(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public List<MyContacts> loadInBackground() {
		List<MyContacts> list = new ArrayList<MyContacts>();
		ContentResolver cr = context.getContentResolver();
		String []projection = new String []{Contacts._ID,Contacts.DISPLAY_NAME};
		Cursor cursor = cr.query(Contacts.CONTENT_URI, projection, null, null, null);
		while(cursor.moveToNext()){
			int id = cursor.getInt(cursor.getColumnIndex(Contacts._ID));
			String name = cursor.getString(cursor.getColumnIndex(Contacts.DISPLAY_NAME));
			Cursor numCursor = context.getContentResolver().query(Phone.CONTENT_URI, 
					new String []{Phone.NUMBER}, 
					Phone.CONTACT_ID+"=?", new String[]{""+id}, null);
			while(numCursor.moveToFirst()){
				String number = numCursor.getString(numCursor.getColumnIndex(Phone.NUMBER));
				MyContacts con = new MyContacts(id, name, number);
				list.add(con);
			}
		}
		return list;
	}
	@Override
	protected void onStartLoading() {
		super.onStartLoading();
		if(this.isStarted()){
			this.forceLoad();
		}
	}
}
