package com.zx.housekeeper.adapter;

import com.zx.housekeeper.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 
 * @author HY
 * 
 */
public class SoftMgrAdapter extends BaseAdapter {

	protected Context mContext;
	protected String[] mTitles;

	public SoftMgrAdapter(Context context, String[] titles) {
		this.mContext = context;
		this.mTitles = titles;
	}

	@Override
	public int getCount() {
		return null != mTitles ? mTitles.length : 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_lst_soft_mgr_sort, null);
			holder.mTxtTitle = (TextView) convertView
					.findViewById(R.id.txt_title_soft);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.mTxtTitle.setText(mTitles[position]);
		return convertView;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	class ViewHolder {
		TextView mTxtTitle;
	}
}
