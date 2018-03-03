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

import java.util.List;

/**
 * Created by hezijie on 2017/1/10.
 */
public class HotGameAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<HotEntity.InfoBean.Push1Bean> mHotGameList;

    public HotGameAdapter(Context context, List<HotEntity.InfoBean.Push1Bean> mHotGameList) {
        this.context = context;
        this.mHotGameList = mHotGameList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mHotGameList==null?0:mHotGameList.size();
    }

    @Override
    public Object getItem(int position) {
        return mHotGameList.get(position);
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
            convertView = inflater.inflate(R.layout.hot_listview_item,parent,false);
            holder.mImage = (ImageView) convertView.findViewById(R.id.hot_lv_iv);
            holder.mGname = (TextView) convertView.findViewById(R.id.hot_lv_gname);
            holder.mGameType = (TextView) convertView.findViewById(R.id.hot_lv_type);
            holder.mGameSize = (TextView) convertView.findViewById(R.id.hot_lv_size);
            holder.mOnLineNum = (TextView) convertView.findViewById(R.id.hot_online_num);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        HotEntity.InfoBean.Push1Bean push1Bean = mHotGameList.get(position);
        String logo = Constants.BASE_URL+push1Bean.getLogo();

        holder.mImage.setTag(logo);
        final ViewHolder finalHolder = holder;
        LoadBtimap.getBitmap(logo, new LoadBtimap.LoadBitmapListener() {
            @Override
            public void disPlayBitmap(Bitmap bitmap, String bitmapUrl) {
                if(finalHolder.mImage.getTag().equals(bitmapUrl)){
                   finalHolder.mImage.setImageBitmap(bitmap);
                }
            }
        });
        holder.mGname.setText(push1Bean.getName());
        holder.mGameType.setText("类型:"+push1Bean.getTypename());
        holder.mGameSize.setText("大小:"+push1Bean.getSize());
        holder.mOnLineNum.setText(push1Bean.getClicks()+"人在玩");
        return convertView;
    }

    class ViewHolder{
        ImageView mImage;
        TextView mGname;
        TextView mGameType;
        TextView mGameSize;
        TextView mOnLineNum;
    }
}
