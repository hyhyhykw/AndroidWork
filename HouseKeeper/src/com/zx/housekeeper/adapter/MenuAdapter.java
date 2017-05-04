package com.zx.housekeeper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.housekeeper.R;

/**
 * main menu adapter
 * 
 * @author HY
 * 
 */
public class MenuAdapter extends BaseAdapter {

	/** image source */
	protected int[] menuIcons;

	/** texts source */
	protected String[] menuTexts;

	/** context object */
	protected Context mContext;

	public MenuAdapter() {
	}

	public MenuAdapter(int[] menuIcons, String[] menuTexts, Context context) {
		super();
		this.menuIcons = menuIcons;
		this.menuTexts = menuTexts;
		this.mContext = context;
	}

	/**
	 * get count of source
	 */
	@Override
	public int getCount() {
		return Math.min(menuIcons.length, menuTexts.length);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (null == convertView) {
			holder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.item_grd_main_menu, null);
			holder.mImgMenuIcon = (ImageView) convertView
					.findViewById(R.id.img_main_menu);
			holder.mTxtMenuText = (TextView) convertView
					.findViewById(R.id.txt_main_menu);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.mImgMenuIcon.setImageResource(menuIcons[position]);
		holder.mTxtMenuText.setText(menuTexts[position]);
		return convertView;
	}

	@Override
	public Object getItem(int position) {
		return null;
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
		/** main menu item icon */
		ImageView mImgMenuIcon;
		/** main menu item title */
		TextView mTxtMenuText;
	}

}
