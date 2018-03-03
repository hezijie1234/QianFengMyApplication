package com.hezijie.loadbitmaputils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;

public class LoadBtimap {
	
	public interface LoadBitmapListener{
		void disPlayBitmap(Bitmap bitmap);
	}
	
	private LoadBitmapListener mListener;
	
	public void getBitmap(String url,LoadBitmapListener mListener){
		this.mListener = mListener;
		new DownBitmap().execute(url);
	}
	
	class DownBitmap extends AsyncTask<String, Void, Bitmap>{

		@TargetApi(Build.VERSION_CODES.KITKAT) @Override
		protected Bitmap doInBackground(String... params) {
			try {
				URL url = new URL(params[0]);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				try(
						InputStream inputStream = conn.getInputStream();
						){
					Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
					return bitmap;
				}
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			return null;
			
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			if(mListener!=null){
				mListener.disPlayBitmap(result);
			}
		}
		
	}
	
}
