package com.example.day15asyncloader;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class MainActivity extends Activity implements LoaderCallbacks<List<MyContacts>>{
	
	private ListView mDataLv;
	private List<MyContacts> mContactsList;
	private ContactAdapter adapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoaderManager manager = getLoaderManager();
        manager.initLoader(1, null, this);
        initView();
    }

	private void initView() {
		MyAsyncTaskLoader loader = new MyAsyncTaskLoader(this);
		mContactsList = new ArrayList<MyContacts>();
		mDataLv = (ListView)findViewById(R.id.load_contact_lv);
		adapter = new ContactAdapter(this, mContactsList);
		mDataLv.setAdapter(adapter);
	}

	@Override
	public Loader<List<MyContacts>> onCreateLoader(int id, Bundle args) {
		if(id==1){
			return new MyAsyncTaskLoader(this);	
		}
		return null;
	}

	@Override
	public void onLoadFinished(Loader<List<MyContacts>> loader,
			List<MyContacts> data) {
		mContactsList.clear();
		mContactsList.addAll(data);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onLoaderReset(Loader<List<MyContacts>> loader) {
		mContactsList.clear();
		mContactsList.addAll(null);
		adapter.notifyDataSetChanged();
	}

    
}
