package com.example.week3test.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.week3test.R;
import com.example.week3test.entity.NewGame;
import com.hezijie.loadbitmaputils.ImageLoader;
import com.hezijie.loadbitmaputils.ImageLoader.OnImageLoadListener;
import com.hezijie.loadbitmaputils.LoadBtimap;
import com.hezijie.loadbitmaputils.LoadBtimap.LoadBitmapListener;

public class NewsAdapter extends BaseAdapter{

	private LayoutInflater inflater;

	private List<NewGame> list;
	
	private LoadBtimap load;
	
	private Context context;

	public NewsAdapter(Context context,List<NewGame> list){
		this.context = context;
		this.list = list;
		this.inflater = LayoutInflater.from(context);
		load = new LoadBtimap();
	}

	@Override
	public int getCount() {
		return null==list ? 0:list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		NewGame news = list.get(position);
		ViewHolder holder;
		if(null==convertView){
			convertView = inflater.inflate(R.layout.newgame_lv, parent,false);
			holder = new ViewHolder();
			holder.backImage = (ImageView)convertView.findViewById(R.id.background_iv);
			holder.orderNo = (TextView)convertView.findViewById(R.id.orderby_tv);
			holder.authorImage = (ImageView)convertView.findViewById(R.id.authorimg_iv);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder)convertView.getTag();
		}
		holder.orderNo.setText(news.getOrderno());
		final ImageView image3 = holder.authorImage;
		final ImageView image2 = holder.backImage;
		new ImageLoader().loadImage(news.getIcomurl(), new OnImageLoadListener() {
			
			@Override
			public void onImageLoadComplete(Bitmap bitmap) {
				if(bitmap != null){
					image2.setImageBitmap(bitmap);
				}
			}
		});
		new ImageLoader().loadImage(news.getAuthorimg(), new OnImageLoadListener() {
			
			@Override
			public void onImageLoadComplete(Bitmap bitmap) {
				if(bitmap != null){
					image3.setImageBitmap(bitmap);
				}
			}
		});
		return convertView;
	}

	static class ViewHolder{
		ImageView backImage;
		ImageView authorImage;
		TextView orderNo;
	}

}
