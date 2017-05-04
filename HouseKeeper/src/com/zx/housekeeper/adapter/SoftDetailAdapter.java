package com.zx.housekeeper.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.housekeeper.R;
import com.zx.housekeeper.entity.SoftInfo;

/**
 * 
 * @author HY
 * 
 */
public class SoftDetailAdapter extends BaseAdapter {

	/** context object */
	protected Context mContext;
	/** adapter source */
	protected List<SoftInfo> softInfos;

	public SoftDetailAdapter(Context context, List<SoftInfo> softInfos) {
		this.mContext = context;
		this.softInfos = softInfos;
	}

	@Override
	public int getCount() {
		return null != softInfos ? softInfos.size() : 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_lst_soft_list, null);

			holder.mImgAppIcon = (ImageView) convertView
					.findViewById(R.id.img_soft_icon);
			holder.mTxtAppName = (TextView) convertView
					.findViewById(R.id.txt_soft_name);
			holder.mTxtPkgName = (TextView) convertView
					.findViewById(R.id.txt_pkg_name);
			holder.mTxtAppVersion = (TextView) convertView
					.findViewById(R.id.txt_version_name);
			holder.mChbDelApp = (CheckBox) convertView
					.findViewById(R.id.chb_select_unins);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		SoftInfo sInfo = getItem(position);
		holder.mImgAppIcon.setImageDrawable(sInfo.getAppIcon());
		holder.mTxtAppName.setText(sInfo.getAppName());
		holder.mTxtPkgName.setText(sInfo.getPkgName());
		holder.mTxtAppVersion.setText(sInfo.getVersionName());
		holder.mChbDelApp.setChecked(sInfo.isChecked());

		return convertView;
	}

	@Override
	public SoftInfo getItem(int position) {
		return softInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public void update(List<SoftInfo> softInfos) {
		this.softInfos = softInfos;
		notifyDataSetChanged();
	}

	/**
	 * 
	 * @author HY
	 * 
	 */
	class ViewHolder {
		/** check box */
		CheckBox mChbDelApp;
		/** application icon */
		ImageView mImgAppIcon;
		/** application name */
		TextView mTxtAppName;
		/** package name */
		TextView mTxtPkgName;
		/** application version */
		TextView mTxtAppVersion;
	}
}
