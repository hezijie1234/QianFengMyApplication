package com.example.myapplication.adapter;

import java.util.List;



import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.entities.Gift;
import com.example.myapplication.utils.Constants;
import com.example.myapplication.utils.LoadBtimap;

public class GiftAdapter extends BaseAdapter{

	private LayoutInflater inflater;

	private List<Gift.ListBean> list;

	private LoadBtimap load;

	private Context context;

	public GiftAdapter(Context context,List<Gift.ListBean> list){
		this.context = context;
		this.list = list;
		load = new LoadBtimap();
		this.inflater = LayoutInflater.from(context);
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
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Gift.ListBean gift = list.get(position);
		String url = gift.getIconurl();
		final ViewHolder holder;
		if(null==convertView){
			convertView = inflater.inflate(R.layout.activity_game, parent,false);
			holder = new ViewHolder();
			holder.giftName = (TextView)convertView.findViewById(R.id.giftname);
			holder.gName = (TextView)convertView.findViewById(R.id.gname);
			holder.numberView = (TextView)convertView.findViewById(R.id.number);
			holder.dateView = (TextView)convertView.findViewById(R.id.date);
			holder.image = (ImageView)convertView.findViewById(R.id.image);
			holder.button2 = (Button) convertView.findViewById(R.id.button2);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder)convertView.getTag();
		}
		holder.image.setTag(Constants.BASE_URL+url);
		LoadBtimap.getBitmap(Constants.BASE_URL+url, new LoadBtimap.LoadBitmapListener() {
			@Override
			public void disPlayBitmap(Bitmap bitmap, String bitmapUrl) {
				if(bitmapUrl.equals(holder.image.getTag())){
					holder.image.setImageBitmap(bitmap);
				}
			}
		});
		holder.gName.setText(gift.getGname());
		holder.giftName.setText(gift.getGiftname());
		//下面的代码中不能写成数字，如果是数字会在字符串资源中寻找id为number的字符串资源
		holder.numberView.setText("剩余:"+String.valueOf(gift.getNumber()));
		holder.dateView.setText(gift.getAddtime());
//		Log.e("111", "getView"+convertView.getTag());
		holder.button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		return convertView;
	}

	static class ViewHolder{
		TextView giftName;
		TextView gName;
		TextView numberView;
		TextView dateView;
		ImageView image;
		Button button2;
	}

}
