package com.zx.housekeeper.adapter;

import com.zx.housekeeper.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author HY
 * 
 */
public class PhoneInfoAdapter extends BaseAdapter {
	/** context object */
	protected Context mContext;
	/** icon source */
	protected int[] mImgIcons;
	/** icon background source */
	protected int[] mImgIconBgs;
	/** phone info source */
	protected String[][] phoneInfos;

	public PhoneInfoAdapter(Context context, int[] mImgIcons,
			int[] mImgIconBgs, String[][] phoneInfos) {
		this.mContext = context;
		this.mImgIcons = mImgIcons;
		this.mImgIconBgs = mImgIconBgs;
		this.phoneInfos = phoneInfos;
	}

	@Override
	public int getCount() {
		return null != mImgIcons ? mImgIcons.length : 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_lst_phone_detection, null);
			holder.mImgIcon = (ImageView) convertView
					.findViewById(R.id.img_phone_det_info);
			holder.mTxtDetTitle = (TextView) convertView
					.findViewById(R.id.txt_phone_det_title);
			holder.mTxtDetContent = (TextView) convertView
					.findViewById(R.id.txt_phone_det_content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.mImgIcon.setImageResource(mImgIcons[position]);
		holder.mImgIcon.setBackgroundResource(mImgIconBgs[position % 3]);
		holder.mTxtDetTitle.setText(phoneInfos[position][0]);
		holder.mTxtDetContent.setText(phoneInfos[position][1]);
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

	public void update(int[] mImgIcons, int[] mImgIconBgs, String[][] phoneInfos) {
		this.mImgIcons = mImgIcons;
		this.mImgIconBgs = mImgIconBgs;
		this.phoneInfos = phoneInfos;
		notifyDataSetChanged();
	}

	/**
	 * 
	 * @author HY
	 * 
	 */
	class ViewHolder {
		/** image icon */
		ImageView mImgIcon;
		/** phone info */
		TextView mTxtDetTitle;
		/** phone info */
		TextView mTxtDetContent;
	}
}
