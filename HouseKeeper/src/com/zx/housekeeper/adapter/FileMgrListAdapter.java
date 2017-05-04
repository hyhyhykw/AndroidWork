package com.zx.housekeeper.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zx.housekeeper.R;
import com.zx.housekeeper.biz.FileUitls;
import com.zx.housekeeper.entity.FileInfo;

/**
 * file manager page list adapter
 * 
 * @author HY
 * 
 */
public class FileMgrListAdapter extends BaseAdapter {

	/** context object */
	protected Context mContext;

	/** file information source */
	protected List<FileInfo> fileInfos;

	public FileMgrListAdapter(Context mContext, List<FileInfo> fileInfos) {
		this.mContext = mContext;
		this.fileInfos = fileInfos;

	}

	@Override
	public int getCount() {
		return null != fileInfos ? fileInfos.size() : 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.item_lst_file_mgr_list,
					null);
			holder.mTxtFileType = (TextView) convertView
					.findViewById(R.id.txt_file_type);
			holder.mTxtFileSize = (TextView) convertView
					.findViewById(R.id.txt_file_total_mem);
			holder.mImgArrow = (ImageView) convertView
					.findViewById(R.id.img_file_mgr_list_arrow);
			holder.mPgbLoad = (ProgressBar) convertView
					.findViewById(R.id.pgb_file_load);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.mTxtFileType.setText(fileInfos.get(position).getFileType());
		String size = FileUitls.formatLength(fileInfos.get(position)
				.getFileTotalSize());
		holder.mTxtFileSize.setText(size);
		if (!mIsFinish) {
			holder.mImgArrow.setVisibility(View.GONE);
			holder.mPgbLoad.setVisibility(View.VISIBLE);
		} else {
			holder.mImgArrow.setVisibility(View.VISIBLE);
			holder.mPgbLoad.setVisibility(View.GONE);
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
	 * update data and refresh ListView
	 */
	public void update(List<FileInfo> fileInfos) {
		this.fileInfos = fileInfos;
		notifyDataSetChanged();
	}

	boolean mIsFinish = false;

	public void update(boolean isFinish) {
		this.mIsFinish = isFinish;
		notifyDataSetChanged();
	}

	/**
	 * 
	 * @author HY
	 * 
	 */
	class ViewHolder {
		/** file's type */
		TextView mTxtFileType;
		/** this file size */
		TextView mTxtFileSize;
		/** image of right arrow */
		ImageView mImgArrow;
		/** loading progress bar */
		ProgressBar mPgbLoad;
	}
}
