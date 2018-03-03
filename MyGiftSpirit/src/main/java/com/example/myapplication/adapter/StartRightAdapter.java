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
import com.example.myapplication.entities.StartSysRight;
import com.example.myapplication.utils.Constants;
import com.example.myapplication.utils.LoadBtimap;

import java.util.List;

/**
 * Created by hezijie on 2017/1/9.
 */
public class StartRightAdapter extends BaseAdapter {

    private List<StartSysRight.InfoBean> list;
    private LayoutInflater inflater;
    private Context context ;

    public StartRightAdapter(Context context, List<StartSysRight.InfoBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
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
        ViewHolder holder = null;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.startsys_right_layout,parent,false);
            holder.imageView = (ImageView) convertView.findViewById(R.id.startsys_right_iv);
            holder.gName = (TextView) convertView.findViewById(R.id.startsys_right_gname_tv);
            holder.operator = (TextView) convertView.findViewById(R.id.startsys_right_operators);
            holder.addTime = (TextView) convertView.findViewById(R.id.startsys_right_addtime);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        StartSysRight.InfoBean infoBean = list.get(position);
        String iconurl = Constants.BASE_URL+infoBean.getIconurl();
        holder.imageView.setTag(iconurl);
        final ViewHolder finalHolder = holder;
        LoadBtimap.getBitmap(iconurl, new LoadBtimap.LoadBitmapListener() {
            @Override
            public void disPlayBitmap(Bitmap bitmap, String bitmapUrl) {
                if(finalHolder.imageView.getTag().equals(bitmapUrl)){
                    finalHolder.imageView.setImageBitmap(bitmap);
                }
            }
        });
        holder.gName.setText(infoBean.getGname());
        holder.operator.setText("运营商："+infoBean.getOperators());
        holder.addTime.setText(infoBean.getAddtime());
        return convertView;
    }
    class ViewHolder {
        ImageView imageView;
        TextView gName;
        TextView operator;
        TextView addTime;
    }
}
