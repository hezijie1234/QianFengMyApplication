package com.androidxx.yangjw.day31_project_mvp_demo.view.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidxx.yangjw.day31_project_mvp_demo.R;
import com.androidxx.yangjw.day31_project_mvp_demo.model.beans.HomeListBean;

import java.util.List;

/**
 * Created by yangjw on 2017/2/3.
 */
public class HomeListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<HomeListBean.ListBean> list;

    public HomeListAdapter(Context context,List<HomeListBean.ListBean> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.item_home_fragment_list,parent,false);
            viewHolder = new ViewHolder(view);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        HomeListBean.ListBean listBean = list.get(position);
        viewHolder.textView.setText(listBean.getName());
        return view;
    }

    class ViewHolder {
        public TextView textView;

        public ViewHolder(View view) {
            view.setTag(this);
            textView = (TextView) view.findViewById(R.id.item_fragment_home_text);
        }
    }
}
