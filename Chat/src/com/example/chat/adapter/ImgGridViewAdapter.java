package com.example.chat.adapter;

import com.example.chat.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * 自定义聊天记录显示adapter
 * 
 * @Copyright Copyright (c) 2012 - 2100
 * @author Administrator
 * @create at 2013-5-9
 * @version 1.1.0
 */
public class ImgGridViewAdapter extends BaseAdapter {
	private Context context;
	private ViewHolder holder;
	private int[] imgId;

	class ViewHolder {
		ImageView img;
	}

	public ImgGridViewAdapter(Context context,int [] imgId) {
		this.context = context;
		this.imgId=imgId;
		holder = new ViewHolder();
	}

	@Override
	public int getCount() {
		return imgId.length;
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
		convertView = LayoutInflater.from(context).inflate(R.layout.img_item,
				null);
		holder.img = (ImageView) convertView.findViewById(R.id.ima);
		holder.img.setImageResource(imgId[position]);

		return convertView;
	}
}
