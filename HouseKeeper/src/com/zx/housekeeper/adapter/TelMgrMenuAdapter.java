package com.zx.housekeeper.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zx.housekeeper.R;

/**
 * telephone manager adapter
 * 
 * @author HY
 * 
 */
public class TelMgrMenuAdapter extends BaseAdapter {

	/** source */
	protected List<String> telMenus;

	/** context object */
	protected Context mContext;

	public TelMgrMenuAdapter() {
	}

	public TelMgrMenuAdapter(List<String> telMenus, Context context) {
		this.telMenus = telMenus;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		return null != telMenus ? telMenus.size() : 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (null == convertView) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.item_grd_tel_mgr_main_menu,
					null);
			holder = new ViewHolder();
			holder.mTxtTelMenu = (TextView) convertView
					.findViewById(R.id.txt_tel_menu_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		switch (position % 3) {
		case 0:// background color red
			convertView
					.setBackgroundResource(R.drawable.tel_mgr_menu_bg_red_selector);
			break;
		case 1:// background color yellow
			convertView
					.setBackgroundResource(R.drawable.tel_mgr_menu_bg_yellow_selector);
			break;
		case 2:// background color green
			convertView
					.setBackgroundResource(R.drawable.tel_mgr_menu_bg_green_selector);
			break;

		}

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				MeasureSpec.getSize(280));
		convertView.setLayoutParams(params);// set widget height

		holder.mTxtTelMenu.setText(telMenus.get(position));

		return convertView;
	}

	@Override
	public Object getItem(int position) {
		return telMenus.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * view holder
	 * 
	 * @author HY
	 * 
	 */
	class ViewHolder {
		/** telephone manager main menu item title */
		TextView mTxtTelMenu;
	}

}
