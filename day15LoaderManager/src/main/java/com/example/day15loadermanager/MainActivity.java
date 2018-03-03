package com.example.day15loadermanager;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends Activity implements LoaderCallbacks<Cursor>{
	
	private ListView mContactsLv;
	private SimpleCursorAdapter mCursorAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

	private void initView() {
		mContactsLv = (ListView)findViewById(R.id.contacts_lv);
		LoaderManager manager = getLoaderManager();
		manager.initLoader(1, null, this);
		mCursorAdapter = new SimpleCursorAdapter(this,
				R.layout.contacts_content,
				null, 
				new String []{Contacts._ID,Contacts.DISPLAY_NAME},
				new int[]{R.id.contact_id_tv,R.id.contact_name_tv}, 
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		mContactsLv.setAdapter(mCursorAdapter);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		if(id==1){
			CursorLoader loader = new CursorLoader(this, 
					Contacts.CONTENT_URI,
					new String []{Contacts._ID,Contacts.DISPLAY_NAME},
					null,
					null, 
					null);
			return loader;
		}
		
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		Cursor oldCursor = mCursorAdapter.swapCursor(data);
		if(oldCursor!=null){
			oldCursor.close();
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		Cursor oldCursor = mCursorAdapter.swapCursor(null);
		if(oldCursor!=null){
			oldCursor.close();
		}
	}  
}
