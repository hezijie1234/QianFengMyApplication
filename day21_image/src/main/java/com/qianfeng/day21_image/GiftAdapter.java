package com.qianfeng.day21_image;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianfeng.day21_image.entity.Gift;
import com.qianfeng.day21_image.utils.ImageLoader;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xray on 16/12/6.
 */

public class GiftAdapter extends BaseAdapter {

    public static final String BASE_URL = "http://www.1688wan.com";
    private List<Gift> mGifts;
    private Context mContext;
    private LayoutInflater mInflater;

    public GiftAdapter(Context context,List<Gift> gifts){
        mContext = context;
        mGifts = gifts;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mGifts == null ? 0 : mGifts.size();
    }

    @Override
    public Object getItem(int position) {
        return mGifts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder viewHolder;
        if(view == null){
            view = mInflater.inflate(R.layout.gift_item,null);
            viewHolder = new ViewHolder();
            viewHolder.mImageIcon = (ImageView) view.findViewById(R.id.image_icon_iv);
            viewHolder.mAddTimeTv = (TextView) view.findViewById(R.id.add_time_tv);
            viewHolder.mGiftNameTv = (TextView) view.findViewById(R.id.gift_name_tv);
            viewHolder.mGnameTv = (TextView) view.findViewById(R.id.game_name_tv);
            viewHolder.mNumberTv = (TextView) view.findViewById(R.id.number_tv);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Gift gift = mGifts.get(position);
        viewHolder.mNumberTv.setText("剩余:"+gift.getNumber());
        viewHolder.mGnameTv.setText(gift.getGname());
        viewHolder.mGiftNameTv.setText(gift.getGiftname());
        viewHolder.mAddTimeTv.setText("时间:"+gift.getAddtime());
        //设置一张默认图片
        viewHolder.mImageIcon.setImageResource(R.mipmap.ic_launcher);
        //将图片的地址保存到ImageView的tag中
        String s = BASE_URL + gift.getIconurl();
        viewHolder.mImageIcon.setTag(s);
        ImageLoader.loadImage(s,
                new ImageLoader.OnImageLoadListener() {
                    @Override
                    public void onImageLoadComplete(Bitmap bitmap,String urlStr) {
                        //把当前加载图片的地址和tag值进行比较
                        if(urlStr.equals(viewHolder.mImageIcon.getTag())) {
                            viewHolder.mImageIcon.setImageBitmap(bitmap);
                        }
                    }
                });
        return view;
    }

    static class ViewHolder{
        ImageView mImageIcon;
        TextView mGnameTv;
        TextView mGiftNameTv;
        TextView mNumberTv;
        TextView mAddTimeTv;
    }
}
