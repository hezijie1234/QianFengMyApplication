package com.example.week3test.json;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;


public class LoaderJsonData {

	public interface GetJsonData{
		void getJsonData(String jsonData);
	}

	private GetJsonData getData;



	public void getGift(String url,GetJsonData getData){
		this.getData = getData;
		new AsyncTaskJson().execute(url);
	}

	class AsyncTaskJson extends AsyncTask<String, Void, String>{

		@TargetApi(Build.VERSION_CODES.KITKAT) @Override
		protected String doInBackground(String... params) {
			URL url;
			try {
				url = new URL(params[0]);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//				try(
//						InputStream inputStream = conn.getInputStream();
//						BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//						){
//					StringBuilder sb = new StringBuilder();
//					char[] c = new char[1024];
//					int length = 0;
//					while((length=reader.read(c))!=-1){
//						String str = new String(c,0,length);
//						sb.append(str);
//					}	
//				}
				try(
						InputStream inputStream = conn.getInputStream();
						){
					int len = 0;
					byte[] byt = new byte[1024];
					StringBuilder sb = new StringBuilder();
					while((len=inputStream.read(byt))!=-1){
						String str = new String(byt);
						sb.append(str);
					}
					return sb.toString();
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if(getData!=null){
			getData.getJsonData(result);
		}
	}
}
}

