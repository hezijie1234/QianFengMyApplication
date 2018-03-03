package com.qianfeng.day19_expandablelistview_base.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by xray on 16/12/29.
 */

public class GroupAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private Map<String,List<String>> mData;

    public GroupAdapter(Context mContext, Map<String, List<String>> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    //获得组的数量
    @Override
    public int getGroupCount() {
        return mData.keySet().size();
    }

    //获得组内部值的数量
    @Override
    public int getChildrenCount(int groupPosition) {
        String key = (String) mData.keySet().toArray()[groupPosition];
        List<String> list = mData.get(key);
        return list == null ? 0 : list.size();
    }

    //获得组的视图
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String group = (String) mData.keySet().toArray()[groupPosition];
        TextView textView = new TextView(mContext);
        textView.setText(group);
        textView.setTextColor(Color.RED);
        textView.setTextSize(20);
        return textView;
    }

    //获得组中的子视图
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String group = (String) mData.keySet().toArray()[groupPosition];
        List<String> list = mData.get(group);
        String child = list.get(childPosition);
        TextView textView = new TextView(mContext);
        textView.setText(child);
        textView.setTextColor(Color.BLUE);
        textView.setTextSize(15);
        return textView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        //设置为true，代表可以进行点击
        return true;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
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
