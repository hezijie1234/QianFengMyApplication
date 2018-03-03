package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.entities.FightWednesDay;
import com.example.myapplication.utils.Constants;
import com.example.myapplication.utils.LoadBtimap;

import java.util.List;

/**
 * Created by hezijie on 2017/1/8.
 */
public class FightWednAdapter extends BaseAdapter {

    private Context context;
    private List<FightWednesDay.ListBean> mList;
    private LayoutInflater mInflater;
    private LoadBtimap mLoadBitmap;

    public FightWednAdapter(Context context, List<FightWednesDay.ListBean> mList) {
        this.context = context;
        this.mList = mList;
        mLoadBitmap = new LoadBtimap();
        this.mInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 :mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView==null){
            convertView = mInflater.inflate(R.layout.tese_left_ly,parent,false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.wednes_image);
            holder.text1 = (TextView) convertView.findViewById(R.id.tese_left_name);
            holder.text2 = (TextView) convertView.findViewById(R.id.tese_left_addtime);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        FightWednesDay.ListBean listBean = mList.get(position);
        String url = Constants.BASE_URL+listBean.getIconurl();
        holder.image.setTag(url);
        final ViewHolder finalHolder = holder;
        LoadBtimap.getBitmap(url, new LoadBtimap.LoadBitmapListener() {
            @Override
            public void disPlayBitmap(Bitmap bitmap, String bitmapUrl) {
                if(bitmapUrl.equals(finalHolder.image.getTag())){
                    if(bitmap!=null){
                        finalHolder.image.setImageBitmap(bitmap);
                    }
                }
            }
        });
        holder.text1.setText(listBean.getName());
        holder.text2.setText(listBean.getAddtime());
        return convertView;
    }

    class ViewHolder{
        ImageView image;
        TextView text1;
        TextView text2;
    }
}
