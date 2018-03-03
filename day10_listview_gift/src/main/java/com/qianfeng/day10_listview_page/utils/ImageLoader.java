package com.qianfeng.day10_listview_page.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 图片加载的工具类
 * @author xray
 *
 */
public class ImageLoader {

	/**
	 * 图片加载完成的回调接口
	 */
	public interface OnImageLoadListener{
		//完成图片加载
		void onImageLoadComplete(Bitmap bitmap,String urlStr);
	}
	

	
	/**
	 * 启动图片加载任务
	 * @param urlStr
	 * @param listener
	 */
	public static void loadImage(String urlStr,OnImageLoadListener listener){
		new ImageLoadTask(listener).execute(urlStr);
	}

	/**
	 * 包装图片和地址
	 */
	static class ImageInfo{
		Bitmap bitmap;
		String urlStr;
		public ImageInfo(Bitmap bitmap, String urlStr) {
			this.bitmap = bitmap;
			this.urlStr = urlStr;
		}
	}

	/**
     * 图片加载任务
     * @author xray
     *
     */
    static class ImageLoadTask extends AsyncTask<String,Void,ImageInfo>{

		private OnImageLoadListener mListener;

		public ImageLoadTask(OnImageLoadListener listener){
			this.mListener = listener;
		}

		@Override
		protected ImageInfo doInBackground(String... params) {
			
			try {
				//创建URL，指定图片地址
				URL url = new URL(params[0]);
				//打开连接获得HttpURLConnection对象
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				//获得文件输入流
				InputStream stream = conn.getInputStream();
				//把输入流转换为图片
				Bitmap bmp = BitmapFactory.decodeStream(stream);
				//关闭流
				stream.close();
				return new ImageInfo(bmp,params[0]);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(ImageInfo result) {
			//进行接口回调
			if(mListener != null){
				mListener.onImageLoadComplete(result.bitmap,result.urlStr);
			}
		}
    	
    }
}
