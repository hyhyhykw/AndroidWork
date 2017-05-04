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
import com.zx.housekeeper.biz.FileUitls;
import com.zx.housekeeper.entity.ProcInfo;

/**
 * 
 * @author HY
 * 
 */
public class ProcListAdapter extends BaseAdapter {

	/**
	 * context object
	 */
	protected Context mContext;
	/**
	 * source of process info
	 */
	protected List<ProcInfo> procInfos;

	public ProcListAdapter(Context mContext, List<ProcInfo> procInfos) {
		this.mContext = mContext;
		this.procInfos = procInfos;
	}

	@Override
	public int getCount() {
		return null != procInfos ? procInfos.size() : 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_lst_progress_detail, null);

			holder.mChbKillSelect = (CheckBox) convertView
					.findViewById(R.id.chb_select_kill);
			holder.mImgIcon = (ImageView) convertView
					.findViewById(R.id.img_process_icon);
			holder.mTxtPrcName = (TextView) convertView
					.findViewById(R.id.txt_process_name);
			holder.mTxtAppMem = (TextView) convertView
					.findViewById(R.id.txt_process_mem);
			holder.mTxtSysProc = (TextView) convertView
					.findViewById(R.id.txt_system_process);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		ProcInfo pInfo = getItem(position);

		holder.mChbKillSelect.setChecked(pInfo.isChecked());
		holder.mImgIcon.setImageDrawable(pInfo.getProcIcon());
		holder.mTxtPrcName.setText(pInfo.getProcName());
		holder.mTxtAppMem.setText("内存:"
				+ FileUitls.formatLength(pInfo.getProcMem()));

		holder.mTxtSysProc.setVisibility(pInfo.isUserApp() ? View.INVISIBLE
				: View.VISIBLE);
		return convertView;
	}

	@Override
	public ProcInfo getItem(int position) {
		return procInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void update(List<ProcInfo> progressInfos) {
		this.procInfos = progressInfos;
		notifyDataSetChanged();
	}

	/**
	 * 
	 * @author HY
	 * 
	 */
	class ViewHolder {
		/** check box kill selected */
		CheckBox mChbKillSelect;
		/** process icon */
		ImageView mImgIcon;
		/** process name */
		TextView mTxtPrcName;
		/** application used memory */
		TextView mTxtAppMem;
		/** text of system process */
		TextView mTxtSysProc;
	}
}
