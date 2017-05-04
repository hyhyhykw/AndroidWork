package com.zx.housekeeper.adapter;

import java.io.File;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.housekeeper.R;
import com.zx.housekeeper.biz.DateUitls;
import com.zx.housekeeper.biz.FileUitls;
import com.zx.housekeeper.biz.IconMap;
import com.zx.housekeeper.entity.FileDetailInfo;

/**
 * file detail adapter
 * 
 * @author HY
 * 
 */
public class FileDetailAdapter extends BaseAdapter {

	/** context object */
	protected Context mContext;
	/** file detail information source */
	protected List<FileDetailInfo> fileDetailInfos;

	protected Map<String, Integer> mFileIcons;

	public FileDetailAdapter(Context mContext,
			List<FileDetailInfo> fileDetailInfos) {
		this.mContext = mContext;
		this.fileDetailInfos = fileDetailInfos;
		mFileIcons = IconMap.getIcon();
	}

	@Override
	public int getCount() {
		return null != fileDetailInfos ? fileDetailInfos.size() : 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.item_lst_file_detail, null);
			holder.mTxtFileName = (TextView) convertView
					.findViewById(R.id.txt_file_name);
			holder.mTxtFileTime = (TextView) convertView
					.findViewById(R.id.txt_file_time);
			holder.mTxtFileSize = (TextView) convertView
					.findViewById(R.id.txt_file_size);
			convertView.setTag(holder);
			holder.mImgFileIcon = (ImageView) convertView
					.findViewById(R.id.img_file_icon);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		FileDetailInfo fileDetailInfo = fileDetailInfos.get(position);
		File file = fileDetailInfo.getFile();
		holder.mTxtFileName.setText(file.getName());
		String suffix = fileDetailInfo.getFileSuffix();
		if (FileUitls.isImageFile(suffix)) {
			holder.mImgFileIcon.setImageURI(Uri.fromFile(file));
		} else if (mFileIcons.containsKey(suffix)) {
			holder.mImgFileIcon.setImageResource(mFileIcons.get(suffix));
		} else {
			holder.mImgFileIcon.setImageResource(R.drawable.icon_file);
		}
		long time = file.lastModified();
		holder.mTxtFileTime.setText(DateUitls.Mills2Date(time));
		holder.mTxtFileSize.setText(FileUitls.formatLength(file.length()));
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

	public void update(List<FileDetailInfo> fileDetailInfos) {
		this.fileDetailInfos = fileDetailInfos;
		notifyDataSetChanged();
	}

	/**
	 * view holder
	 * 
	 * @author HY
	 * 
	 */
	class ViewHolder {
		/** file name */
		TextView mTxtFileName;
		/** file last modify time */
		TextView mTxtFileTime;
		/** file total size */
		TextView mTxtFileSize;
		/** file icon */
		ImageView mImgFileIcon;

	}
}
