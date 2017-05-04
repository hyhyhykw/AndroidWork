package com.zx.housekeeper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.zx.housekeeper.adapter.FileMgrListAdapter;
import com.zx.housekeeper.biz.FileManager;
import com.zx.housekeeper.biz.FileUitls;
import com.zx.housekeeper.entity.FileInfo;

/**
 * file manager main page
 * 
 * @author HY
 * 
 */
public class FileMgrActivity extends BaseActivity {
	/** ListView Adapter */
	protected FileMgrListAdapter adapter;

	/** handle message from child thread */
	protected Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String size = FileUitls.formatLength(fileInfos.get(0)
					.getFileTotalSize());
			mTxtTotalFileMem.setText(size);
			adapter.update(fileInfos);
			if (msg.what == 0) {
				adapter.update(true);
			}
		};
	};

	/** extend storage path */
	protected File mSdcardFile;
	/** left image button */
	protected ImageView mImgReturn;

	/** right image button */
	protected ImageView mImgSetting;

	/** text title */
	protected TextView mTxtTitle;

	/** ListView */
	protected ListView mLstFileTypeList;

	/** file types resource */
	protected String[] fileTypes;

	/** file types source */
	protected List<FileInfo> fileInfos;

	/** file total memory */
	protected TextView mTxtTotalFileMem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_mgr);
		initData();

	}

	@Override
	protected void onResume() {
		super.onResume();
		initView();
		initTitle(R.drawable.btn_homeasup_default, View.INVISIBLE);
		initEvent();

		mSdcardFile = Environment.getExternalStorageDirectory();
		FileManager fileManager = FileManager.getInstance();
		fileManager.search(fileInfos, mHandler, mSdcardFile);

	}

	/**
	 * initialize view
	 */
	public void initView() {

		mImgReturn = (ImageView) this.findViewById(R.id.img_title_left);
		mTxtTitle = (TextView) this.findViewById(R.id.txt_title);
		mImgSetting = (ImageView) this.findViewById(R.id.img_title_right);
		mLstFileTypeList = (ListView) this.findViewById(R.id.lst_file_mgr_list);
		mTxtTotalFileMem = (TextView) this
				.findViewById(R.id.txt_file_total_mem);
		adapter = new FileMgrListAdapter(FileMgrActivity.this, fileInfos);
		mLstFileTypeList.setAdapter(adapter);
	}

	/**
	 * set widget source
	 * 
	 * @param resId
	 * @param txtId
	 * @param visible
	 */
	public void initTitle(int resId, int visible) {
		mImgReturn.setImageResource(resId);
		String title = this.getIntent().getBundleExtra("bundle")
				.getString("title");
		mTxtTitle.setText(title);
		mImgSetting.setVisibility(visible);
	}

	/**
	 * initialize data
	 */
	public void initData() {

		fileInfos = new ArrayList<FileInfo>();
		fileTypes = this.getResources().getStringArray(R.array.txt_file_types);
		for (int i = 0; i < fileTypes.length; i++) {
			FileInfo fileInfo = new FileInfo();
			fileInfo.setFileType(fileTypes[i]);
			fileInfos.add(fileInfo);
		}

	}

	/**
	 * set widget click event
	 */
	public void initEvent() {
		/** left image button click event */
		mImgReturn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		/** file list item click event */
		mLstFileTypeList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Bundle bundle = new Bundle();
						bundle.putInt("position", position);
						String fileType = fileTypes[position];
						bundle.putString("title", fileType);

						toActivity(FileDetailActivity.class, bundle);
					}
				});
	}
}
