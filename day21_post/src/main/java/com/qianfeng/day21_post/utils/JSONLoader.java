package com.qianfeng.day21_post.utils;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * 读取服务器JSON的工具类
 * @author xray
 *
 */
public class JSONLoader {
	
	public interface OnJSONLoadListener{
		void onJSONLoadComplete(String json);
	}
	
	private OnJSONLoadListener mListener;
	
	public void loadJSON(String urlStr,OnJSONLoadListener listener){
		this.mListener = listener;
		new JSONLoadTask().execute(urlStr);
	}

	public void loadJSONForPost(String urlStr,List<String> params,OnJSONLoadListener listener){
		this.mListener = listener;
		new JSONLoadPostTask(params).execute(urlStr);
	}

	/**
	 * 执行GET请求的异步任务
	 */
	class JSONLoadTask extends AsyncTask<String,Void,String>{

		@Override
		protected String doInBackground(String... params) {
			try {
				URL url = new URL(params[0]);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				InputStream inputStream = conn.getInputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				//StringBuffer 线程安全，性能低 VS StringBuilder 线程不安全，性能高
				StringBuilder strB = new StringBuilder();
				while((len = inputStream.read(buffer)) != -1){
					//将字节码转换为String
					String str = new String(buffer,0,len);
					strB.append(str);
				}
				inputStream.close();
				return strB.toString();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if(mListener != null){
				mListener.onJSONLoadComplete(result);
			}
		}
	}

	/**
	 * 执行Post请求的异步任务
	 */
	class JSONLoadPostTask extends AsyncTask<String,Void,String>{

		//Post请求的参数集合
		private List<String> mParams;

		public JSONLoadPostTask(List<String> mParams){
			this.mParams = mParams;
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				URL url = new URL(params[0]);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				if(mParams != null){
					//提交post的参数给服务器
					OutputStream outputStream = conn.getOutputStream();
					for(String param : mParams){
						outputStream.write(param.getBytes());
					}
					outputStream.flush();
					outputStream.close();
				}
				InputStream inputStream = conn.getInputStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				//StringBuffer 线程安全，性能低 VS StringBuilder 线程不安全，性能高
				StringBuilder strB = new StringBuilder();
				while((len = inputStream.read(buffer)) != -1){
					//将字节码转换为String
					String str = new String(buffer,0,len);
					strB.append(str);
				}
				inputStream.close();
				return strB.toString();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if(mListener != null){
				mListener.onJSONLoadComplete(result);
			}
		}

	}
}
