package com.example.chat.adapter;

import java.util.List;

import com.example.chat.MessageModel;
import com.example.chat.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 自定义聊天记录显示adapter
 * 
 * @Copyright Copyright (c) 2012 - 2100
 * @author Administrator
 * @create at 2013-5-9
 * @version 1.1.0
 */
public class MessageAdapter extends BaseAdapter {
	private List<MessageModel> listModel;
	private Context context;
	private ViewHolder holder;

	class ViewHolder {
		TextView messageA;
		TextView dateA;
		TextView messageB;
		TextView dateB;
		ImageView imgA;
		ImageView imgB;
		LinearLayout layoutB;
		LinearLayout layoutA;

	}

	public MessageAdapter(Context context, List<MessageModel> listModel) {
		this.context = context;
		this.listModel = listModel;
		holder = new ViewHolder();
	}

	@Override
	public int getCount() {
		return listModel.size();
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
		convertView = LayoutInflater.from(context).inflate(R.layout.list_item,
				null);
		holder.messageA = (TextView) convertView.findViewById(R.id.messageA);
		holder.dateA = (TextView) convertView.findViewById(R.id.dateA);
		holder.messageB = (TextView) convertView.findViewById(R.id.messageB);
		holder.dateB = (TextView) convertView.findViewById(R.id.dateB);
		holder.layoutB = (LinearLayout) convertView.findViewById(R.id.layoutB);
		holder.layoutA = (LinearLayout) convertView.findViewById(R.id.layoutA);
		holder.imgA = (ImageView) convertView.findViewById(R.id.imgA);
		holder.imgB = (ImageView) convertView.findViewById(R.id.imgB);
		if (listModel.get(position).isA()) {// 如果是A发送的消息 则隐藏B的布局
			holder.layoutB.setVisibility(View.GONE);
			holder.messageA.setText(listModel.get(position).getMessage());
			holder.dateA.setText(listModel.get(position).getDate());
			holder.imgA.setImageResource(listModel.get(position).getImgId());

		} else {// 不是A发送的消息就隐藏A布局
			holder.layoutA.setVisibility(View.GONE);
			holder.messageB.setText(listModel.get(position).getMessage());
			holder.dateB.setText(listModel.get(position).getDate());
			holder.imgB.setImageResource(listModel.get(position).getImgId());
		}
		return convertView;
	}

}
