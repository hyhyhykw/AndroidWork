package com.zx.housekeeper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.zx.housekeeper.R;

/**
 * setting page switch adapter
 * 
 * @author HY
 * 
 */
public class SettingAdapter extends BaseAdapter {

	/** context object */
	protected Context mContext;

	/** widget texts source */
	protected String[] settingSwitchTxts;

	public SettingAdapter(Context mContext, String[] settingSwitchTxts) {
		this.mContext = mContext;
		this.settingSwitchTxts = settingSwitchTxts;
	}

	@Override
	public int getCount() {
		return null != settingSwitchTxts ? settingSwitchTxts.length : 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater
					.inflate(R.layout.item_lst_setting_list, null);
			holder.mTxtItem = (TextView) convertView
					.findViewById(R.id.txt_set_item);
			holder.mChbSwitch = (CheckBox) convertView
					.findViewById(R.id.chb_switch);
			holder.mImgArrow = (ImageView) convertView
					.findViewById(R.id.img_set_arrow);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.mTxtItem.setText(settingSwitchTxts[position]);
		if (position <= 2) {
			holder.mImgArrow.setVisibility(View.INVISIBLE);
			holder.mChbSwitch.setVisibility(View.VISIBLE);
		} else {
			holder.mImgArrow.setVisibility(View.VISIBLE);
			holder.mChbSwitch.setVisibility(View.INVISIBLE);
		}
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

	/**
	 * 
	 * @author HY
	 * 
	 */
	class ViewHolder {
		/** title of setting item */
		TextView mTxtItem;
		/***/
		CheckBox mChbSwitch;
		/***/
		ImageView mImgArrow;
	}
}
