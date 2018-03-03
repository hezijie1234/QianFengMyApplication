package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.entities.StartSysLeftE;
import com.example.myapplication.utils.Constants;
import com.example.myapplication.utils.LoadBtimap;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

/**
 * Created by hezijie on 2017/1/10.
 */
public class StartSysLeftAdapter extends BaseExpandableListAdapter{

    private Map<String ,List<StartSysLeftE.InfoBean>> map;
    private Context context;
    private LayoutInflater inflater;

    public StartSysLeftAdapter(Map<String, List<StartSysLeftE.InfoBean>> map, Context context) {
        this.map = map;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return map.keySet().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String o = (String) map.keySet().toArray()[groupPosition];
        List<StartSysLeftE.InfoBean> infoBeen = map.get(o);
        return infoBeen.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return  null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {


        return null;
    }
    class ViewHolder{
        ImageView imageView;
        TextView gName;
        TextView linkUrl;
        TextView opertors;
        TextView area;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.start_sys_left_layout,parent,false);
            holder.imageView = (ImageView) convertView.findViewById(R.id.start_sys_left_iv);
            holder.gName= (TextView) convertView.findViewById(R.id.startsys_left_gname_tv);
            holder.linkUrl = (TextView) convertView.findViewById(R.id.startsys_left_addtime);
            holder.opertors = (TextView) convertView.findViewById(R.id.startsys_left_operators);
            holder.area = (TextView) convertView.findViewById(R.id.start_sys_left_area);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        String key = (String) map.keySet().toArray()[groupPosition];
        List<StartSysLeftE.InfoBean> infoBeen = map.get(key);
        StartSysLeftE.InfoBean infoBean = infoBeen.get(childPosition);
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
        holder.linkUrl.setText(infoBean.getLinkurl());
        holder.opertors.setText(infoBean.getOperators());
        holder.area.setText(infoBean.getArea());
        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TextView text = new TextView(context);
        text.setText(map.keySet().toArray()[groupPosition].toString());
        text.setTextColor(Color.BLUE);
        text.setTextSize(15);
        return text;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
