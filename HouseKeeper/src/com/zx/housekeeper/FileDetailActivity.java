package com.zx.housekeeper;

import java.io.File;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zx.housekeeper.adapter.FileDetailAdapter;
import com.zx.housekeeper.biz.FileManager;
import com.zx.housekeeper.biz.FileUitls;
import com.zx.housekeeper.entity.FileDetailInfo;

public class FileDetailActivity extends BaseActivity {
	/** left image button */
	protected ImageView mImgReturn;

	/** right image button */
	protected ImageView mImgSetting;

	/** text title */
	protected TextView mTxtTitle;

	/** list adapter source */
	protected List<FileDetailInfo> fileDetailInfos;

	/** file list widget */
	protected ListView mLstDetailList;

	/** text of file total number */
	protected TextView mTxtFileNum;

	/** text of file total size */
	protected TextView mTxtFileSize;

	/** delete selected file button */
	protected Button mBtnDel;

	/** file detail list adapter */
	protected FileDetailAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_mgr_detail);

		// get data from FileMgrActivity
		Bundle bundle = this.getIntent().getBundleExtra("bundle");
		int position = bundle.getInt("position");
		String title = bundle.getString("title");

		FileManager fileManager = FileManager.getInstance();
		fileDetailInfos = fileManager.getDetailByPos(position);
		initView();
		initTitle(R.drawable.btn_homeasup_default, title, View.INVISIBLE);

		initEvent();
		initMemAndNum();
	}

	/** initial text of memory and number */
	public void initMemAndNum() {
		mTxtFileNum.setText(fileDetailInfos.size() + "个");
		mTxtFileSize.setText(FileUitls.formatLength(calculateMem()));

	}

	/** sum occupy memory */
	protected long sum = 0;

	/**
	 * calculate summary memory
	 * 
	 * @return
	 */
	public long calculateMem() {
		sum = 0;
		for (FileDetailInfo fileDetailInfo : fileDetailInfos) {
			sum += fileDetailInfo.getFile().length();
		}
		return sum;
	}

	/**
	 * initialize view
	 */
	public void initView() {
		mLstDetailList = (ListView) this
				.findViewById(R.id.lst_file_detail_list);
		mImgReturn = (ImageView) this.findViewById(R.id.img_title_left);
		mTxtTitle = (TextView) this.findViewById(R.id.txt_title);
		mImgSetting = (ImageView) this.findViewById(R.id.img_title_right);
		mTxtFileNum = (TextView) this.findViewById(R.id.txt_file_number);
		mTxtFileSize = (TextView) this.findViewById(R.id.txt_occupy_mem);
		mBtnDel = (Button) this.findViewById(R.id.btn_del_select);
		adapter = new FileDetailAdapter(this, fileDetailInfos);
		mLstDetailList.setAdapter(adapter);
	}

	/**
	 * set widget source
	 * 
	 * @param resId
	 * @param txtId
	 * @param visible
	 */
	public void initTitle(int resId, String title, int visible) {
		mImgReturn.setImageResource(resId);
		mTxtTitle.setText(title);
		mImgSetting.setVisibility(visible);

	}

	/**
	 * set widget click event
	 */
	public void initEvent() {
		// set return button click event
		mImgReturn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// set file detail list item click event
		mLstDetailList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				FileDetailInfo fileDetailInfo = fileDetailInfos.get(position);
				CheckBox chbSelected = (CheckBox) view
						.findViewById(R.id.chb_select);

				if (fileDetailInfo.isChecked()) {
					fileDetailInfo.setChecked(false);
					chbSelected.setChecked(false);
				} else {
					fileDetailInfo.setChecked(true);
					chbSelected.setChecked(true);
				}
			}
		});

		// set file detail list item long click event
		mLstDetailList
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						Intent intent = new Intent(Intent.ACTION_VIEW);
						FileDetailInfo fileDetailInfo = fileDetailInfos
								.get(position);
						File file = fileDetailInfo.getFile();
						intent.setDataAndType(Uri.fromFile(file),
								FileUitls.getMimeType(file));
						startActivity(intent);
						return false;
					}
				});
		// set delete button click event
		mBtnDel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				for (int i = 0; i < fileDetailInfos.size(); i++) {
					FileDetailInfo fileDetailInfo = fileDetailInfos.get(i);
					if (fileDetailInfo.isChecked()) {
						File delFile = fileDetailInfo.getFile();
						boolean isDeleted = delFile.delete();
						if (isDeleted) {
							fileDetailInfos.remove(i);
							adapter.update(fileDetailInfos);
							initMemAndNum();
						}
					}
				}

			}
		});
	}
}
