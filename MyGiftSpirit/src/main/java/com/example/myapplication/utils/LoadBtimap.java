package com.example.myapplication.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;

public class LoadBtimap {

	public interface LoadBitmapListener{
		void disPlayBitmap(Bitmap bitmap,String bitmapUrl);
	}

	/**可以将图片按照要求进行压缩
	 * @param url
	 * @param destWidth
	 * @param destHeight
	 * @param mListener
	 */
	public static void getBitmap(String url,int destWidth,int destHeight,LoadBitmapListener mListener){
		new DownBitmap(mListener,destWidth,destHeight).execute(url);
	}

	public static void getBitmap(String url, LoadBitmapListener mListener){
		new DownBitmap(mListener).execute(url);
	}
	static class BitmapInfo{
		Bitmap bitmap;
		String bitmapUrl;

		public BitmapInfo(Bitmap bitmap, String bitmapUrl) {
			this.bitmap = bitmap;
			this.bitmapUrl = bitmapUrl;
		}
	}

	static class DownBitmap extends AsyncTask<String, Void, BitmapInfo>{
		LoadBitmapListener mListener;
		int width;
		int height;
		public DownBitmap(LoadBitmapListener mListener){
			this.mListener = mListener;
		}

		public DownBitmap(LoadBitmapListener mListener, int width, int height) {
			this.mListener = mListener;
			this.width = width;
			this.height = height;
		}

		protected BitmapInfo doInBackground(String... params) {
			Bitmap bitmap = null;
			bitmap = BitmapCache.getFromMemory(params[0]);
			if(bitmap==null){
				//当一级缓存为空的时候就获取二级缓存的图片
				bitmap = BitmapCache.getFromDisk(params[0]);
				if(bitmap==null){
					//二级缓存为空就从网上下载,并且把数据存储到一级和二级缓存
					try {
						URL url = new URL(params[0]);
						HttpURLConnection conn = (HttpURLConnection)url.openConnection();
						try(
								InputStream inputStream = conn.getInputStream();
						){
							if(width==0 || height==0){
								bitmap = BitmapFactory.decodeStream(inputStream);
							}else{
								byte[] bytes = BitmapZip.stream2ByteArray(inputStream);
								bitmap = BitmapZip.bitmapZip(bytes, width, height);
							}
							BitmapCache.save2Memory(params[0],bitmap);
							BitmapCache.save2Disk(params[0],bitmap);
						}
					} catch (Exception e) {

						e.printStackTrace();
					}
				}else{
					//二级缓存不为空的时候就把数据存储到一级缓存
					BitmapCache.save2Memory(params[0],bitmap);
				}
			}
		return new BitmapInfo(bitmap,params[0]);
		}

		@Override
		protected void onPostExecute(BitmapInfo result) {
			super.onPostExecute(result);
			if(result!=null){
				mListener.disPlayBitmap(result.bitmap,result.bitmapUrl);
			}
		}

	}

}
