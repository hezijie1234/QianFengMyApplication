package com.example.day15asyncloader;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContactAdapter extends BaseAdapter{
	
	private List<MyContacts> mDataList;
	private Context context;
	private LayoutInflater inflater;
	
	public ContactAdapter(Context context,List<MyContacts> list){
		this.mDataList = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mDataList==null? 0 : mDataList.size();
	}

	@Override
	public Object getItem(int position) {
		return mDataList.get(position);
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
			convertView = inflater.inflate(R.layout.contacts_, parent,false);
			holder.contactId = (TextView) convertView.findViewById(R.id.contact_id_tv);
			holder.contactName = (TextView)convertView.findViewById(R.id.contact_name_tv);
			holder.contactNum = (TextView)convertView.findViewById(R.id.contact_number_tv);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		MyContacts contact = mDataList.get(position);
		holder.contactId.setText(contact.getId());
		holder.contactName.setText(contact.getName());
		holder.contactNum.setText(""+contact.getNumber());
		return convertView;
	}
	static class ViewHolder{
		TextView contactId;
		TextView contactName;
		TextView contactNum;
		
	}
}
