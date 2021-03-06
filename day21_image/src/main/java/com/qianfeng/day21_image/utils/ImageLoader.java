package com.qianfeng.day21_image.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

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
		void onImageLoadComplete(Bitmap bitmap, String urlStr);
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
	 * 启动图片加载任务(带压缩功能)
	 * @param urlStr
	 * @param listener
	 */
	public static void loadImage(String urlStr,int width,int height,OnImageLoadListener listener){
		new ImageLoadTask(listener,width,height).execute(urlStr);
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
		private int mDestWidth; //压缩的目标宽度
		private int mDestHeight;//压缩的目标高度

		public ImageLoadTask(OnImageLoadListener listener){
			this.mListener = listener;
		}

		public ImageLoadTask(OnImageLoadListener listener,int mDestWidth,int mDestHeight){
			this.mListener = listener;
			this.mDestHeight = mDestHeight;
			this.mDestWidth = mDestWidth;
		}

		@Override
		protected ImageInfo doInBackground(String... params) {
			//1、首先从一级缓存中读取图片，如果找到图片就进行显示
			Bitmap bitmap = null;
			bitmap = ImageUtils.readFromMemoryCache(params[0]);
			if(bitmap == null){
				//2、如果一级缓存没有，再从二级缓存读取，
				bitmap = ImageUtils.readFromDiskCache(params[0]);
				if(bitmap == null){
					//3、如果二级缓存没有，就从网络读取，读取图片后，
					try {
						//创建URL，指定图片地址
						URL url = new URL(params[0]);
						//打开连接获得HttpURLConnection对象
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						//获得文件输入流
						InputStream stream = conn.getInputStream();
						//把输入流转换为图片
						//判断是否需要进行图片压缩
						if(mDestHeight == 0 || mDestWidth == 0){
							bitmap = BitmapFactory.decodeStream(stream);
						}else{
							//对图片进行压缩
							byte[] bytes = ImageUtils.streamToByteArray(stream);
							bitmap = ImageUtils.zipImage(bytes,mDestWidth,mDestHeight);
						}
						//关闭流
						stream.close();
						Log.i("xxx", bitmap+" read from network: "+params[0]);
						//将图片保存到一级和二级缓存中
						if(bitmap != null){
							ImageUtils.saveToMemoryCache(params[0],bitmap);
							ImageUtils.saveToDiskCache(params[0],bitmap);
						}
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else{
					Log.i("xxx", bitmap+" read from disk: "+params[0]);
					//如果找到图片就进行显示，并将图片保存到一级缓存
					ImageUtils.saveToMemoryCache(params[0],bitmap);
				}
			}else{
				Log.i("xxx", bitmap+" read from memory: "+params[0]);
			}
			if(bitmap != null){
				return new ImageInfo(bitmap,params[0]);
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
