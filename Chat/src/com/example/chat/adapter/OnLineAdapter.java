package com.example.chat.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.chat.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 在线人数的adapter
 * 
 * @Copyright Copyright (c) 2012 - 2100
 * @author Administrator
 * @create at 2013-7-11
 * @version 1.1.0
 */
public class OnLineAdapter extends BaseAdapter {
	private List<String> lists = new ArrayList<String>();
	private ViewHolder holder;
	private LayoutInflater flater;

	public OnLineAdapter(List<String> lists, Context context) {
		this.lists = lists;
		holder = new ViewHolder();
		flater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		convertView = flater.inflate(R.layout.onlien_item, null);
		holder.name = (TextView) convertView.findViewById(R.id.name);
		holder.name.setText(lists.get(position));
		return convertView;
	}

	class ViewHolder {
		TextView name;
	}
}
