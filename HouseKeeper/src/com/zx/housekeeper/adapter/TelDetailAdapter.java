package com.zx.housekeeper.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zx.housekeeper.R;
import com.zx.housekeeper.entity.TelInfo;

/**
 * telephone number detail adapter
 * 
 * @author HY
 * 
 */
public class TelDetailAdapter extends BaseAdapter {

	/** widget resource */
	protected List<TelInfo> telInfos;

	/** context object */
	protected Context mContext;

	public TelDetailAdapter(Context context, List<TelInfo> telInfos) {
		this.mContext = context;
		this.telInfos = telInfos;
	}

	@Override
	public int getCount() {
		return null != telInfos ? telInfos.size() : 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.item_lst_tel_number, null);
			holder.mTxtItemName = (TextView) convertView
					.findViewById(R.id.txt_item_name);
			holder.mTxtItemNum = (TextView) convertView
					.findViewById(R.id.txt_item_number);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.mTxtItemName.setText(telInfos.get(position).itemName);
		holder.mTxtItemNum.setText(telInfos.get(position).itemNum);
		return convertView;
	}

	@Override
	public Object getItem(int position) {
		return telInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 
	 * @author HY
	 * 
	 */
	class ViewHolder {
		/** contact name */
		TextView mTxtItemName;
		/** telephone number */
		TextView mTxtItemNum;
	}
}
