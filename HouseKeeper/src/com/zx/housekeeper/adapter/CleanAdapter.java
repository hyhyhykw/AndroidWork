package com.zx.housekeeper.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.housekeeper.R;
import com.zx.housekeeper.biz.FileUitls;
import com.zx.housekeeper.entity.CleanChildInfo;
import com.zx.housekeeper.entity.CleanGroupInfo;

/**
 * 
 * @author HY
 * 
 */
public class CleanAdapter extends BaseExpandableListAdapter {

	/** group source */
	protected List<CleanGroupInfo> groupInfos;

	/** child source */
	protected List<List<CleanChildInfo>> childInfos;

	protected Context mContext;

	public CleanAdapter(Context context, List<CleanGroupInfo> groupInfos,
			List<List<CleanChildInfo>> childInfos) {
		this.mContext = context;
		this.groupInfos = groupInfos;
		this.childInfos = childInfos;
	}

	@Override
	public int getGroupCount() {
		return null != groupInfos ? groupInfos.size() : 0;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return null != childInfos ? childInfos.get(groupPosition).size() : 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		convertView = LayoutInflater.from(mContext).inflate(
				R.layout.item_clv_clean_group, null);
		TextView txtTitle = (TextView) convertView
				.findViewById(R.id.txt_clv_group);
		TextView txtTotalMem = (TextView) convertView
				.findViewById(R.id.txt_clean_total);

		CleanGroupInfo groupInfo = getGroup(groupPosition);
		txtTitle.setText(groupInfo.getGroupTitle());
		txtTotalMem
				.setText(FileUitls.formatLength(groupInfo.getGroupTotalMem()));
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		convertView = LayoutInflater.from(mContext).inflate(
				R.layout.item_clv_clean_child, null);
		ImageView imgAppIcon = (ImageView) convertView
				.findViewById(R.id.img_app_icon);
		TextView txtAppName = (TextView) convertView
				.findViewById(R.id.txt_app_name);
		TextView txtAppMem = (TextView) convertView
				.findViewById(R.id.txt_app_clean_mem);
		CheckBox chbSelectedDel = (CheckBox) convertView
				.findViewById(R.id.chb_selete_clean);
		CleanChildInfo childInfo = getChild(groupPosition, childPosition);
		chbSelectedDel.setChecked(childInfo.isChecked());
		imgAppIcon.setImageDrawable(childInfo.getAppIcon());
		txtAppName.setText(childInfo.getAppName());
		txtAppMem.setText(FileUitls.formatLength(childInfo.getAppTotelMem()));
		return convertView;
	}

	@Override
	public CleanGroupInfo getGroup(int groupPosition) {
		return groupInfos.get(groupPosition);
	}

	@Override
	public CleanChildInfo getChild(int groupPosition, int childPosition) {
		return childInfos.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	/**
	 * 
	 * @param groupInfos
	 * @param childInfos
	 */
	public void update(List<CleanGroupInfo> groupInfos,
			List<List<CleanChildInfo>> childInfos) {
		this.childInfos = childInfos;
		notifyDataSetChanged();
	}

}
