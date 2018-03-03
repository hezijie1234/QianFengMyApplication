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
import com.example.myapplication.entities.Gift;
import com.example.myapplication.entities.NewGame;
import com.example.myapplication.utils.Constants;
import com.example.myapplication.utils.LoadBtimap;

import java.util.List;

/**
 * Created by hezijie on 2017/1/9.
 */
public class NewGameAdapter extends BaseAdapter{

    private Context context;
    private List<NewGame.ListBean> list;
    private LayoutInflater inflater;

    public NewGameAdapter(Context context, List<NewGame.ListBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
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
            convertView = inflater.inflate(R.layout.tese_new_game,parent,false);
            holder.image = (ImageView) convertView.findViewById(R.id.tese_right_iv);
            holder.textView = (TextView) convertView.findViewById(R.id.tese_right_tv);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        NewGame.ListBean listBean = list.get(position);
        String iconurl = Constants.BASE_URL+listBean.getIconurl();
        holder.image.setTag(iconurl);
        final ViewHolder finalHolder = holder;
        LoadBtimap.getBitmap(iconurl, new LoadBtimap.LoadBitmapListener() {
            @Override
            public void disPlayBitmap(Bitmap bitmap, String bitmapUrl) {
                if(finalHolder.image.getTag().equals(bitmapUrl)){
                    finalHolder.image.setImageBitmap(bitmap);
                }
            }
        });
        holder.textView.setText(listBean.getName());

        return convertView;
    }
    class ViewHolder{
        ImageView image;
        TextView textView;
    }
}
