package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.utils.Constants;
import com.example.myapplication.utils.LoadBtimap;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by hezijie on 2017/1/10.
 */
public class HotGridAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mInflater;
    private List<HotEntity.InfoBean.Push2Bean> mList;

    public HotGridAdapter(Context context, List<HotEntity.InfoBean.Push2Bean> mList) {
        this.context = context;
        this.mList = mList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList==null?0:mList.size();
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
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.hot_item,parent,false);
            holder.mGridImage = (ImageView) convertView.findViewById(R.id.hot_grid_iv);
            holder.mGanemName = (TextView) convertView.findViewById(R.id.hot_grid_tv);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        HotEntity.InfoBean.Push2Bean push1Bean = mList.get(position);
        String logo = Constants.BASE_URL+push1Bean.getLogo();
        holder.mGridImage.setTag(logo);
        final ViewHolder finalHolder = holder;
        LoadBtimap.getBitmap(logo, new LoadBtimap.LoadBitmapListener() {
            @Override
            public void disPlayBitmap(Bitmap bitmap, String bitmapUrl) {
                if(finalHolder.mGridImage.getTag().equals(bitmapUrl)){
                    finalHolder.mGridImage.setImageBitmap(bitmap);
                }
            }
        });
        holder.mGanemName.setText(push1Bean.getName());
        return convertView;
    }

    class ViewHolder{
        ImageView mGridImage;
        TextView mGanemName;
    }
}
