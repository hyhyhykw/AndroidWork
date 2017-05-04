package com.zx.housekeeper;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zx.housekeeper.adapter.SoftMgrAdapter;
import com.zx.housekeeper.biz.FileUitls;
import com.zx.housekeeper.biz.MemManager;
import com.zx.housekeeper.view.PieChartView;

public class SoftMgrActivity extends BaseActivity {

	/** left image button */
	protected ImageView mImgReturn;

	/** right image button */
	protected ImageView mImgSetting;

	/** text title */
	protected TextView mTxtTitle;

	/** source */
	protected String[] mTitles;

	/** soft sort list */
	protected ListView mLstSoftTite;

	/** pie chart */
	protected PieChartView mPieView;

	/** outer storage used memory and unused memory */
	protected TextView mTxtOuterSdMem;

	/** inner storage used memory and unused memory */
	protected TextView mTxtInnerSdMem;
	/** outer and inner storage path */
	protected String[] paths;

	/** inner sdcard used */
	protected ProgressBar mPgbInner;

	/** outer sdcard used */
	protected ProgressBar mPgbOuter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_soft_mgr);
		initView();
		initData();
		initTitle(R.drawable.btn_homeasup_default,
				View.INVISIBLE);
		initEvent();

	}

	/**
	 * initial data
	 */
	public void initData() {

		mTitles = this.getResources().getStringArray(R.array.lst_soft_title);
		SoftMgrAdapter adapter = new SoftMgrAdapter(this, mTitles);
		mLstSoftTite.setAdapter(adapter);

		paths = FileUitls.getPaths(this);

		mPieView.setAngle(paths);
		if (null == paths[1]) {
			Toast.makeText(this, "外置存储异常", Toast.LENGTH_SHORT).show();
		}
		mTxtInnerSdMem.setText(getAvailMem(0));
		mTxtOuterSdMem.setText(getAvailMem(1));

		int innerPercent = (int) (MemManager.getUsedPercent(paths[0]) * 100);
		mPgbInner.setProgress(innerPercent);
		int outerPercent = (int) (MemManager.getUsedPercent(paths[1]) * 100);
		mPgbOuter.setProgress(outerPercent);
	}

	/**
	 * get available memory
	 * 
	 * @param index
	 * @return
	 */
	public String getAvailMem(int index) {
		if (null != paths[index]) {
			String path = paths[index];
			String total = FileUitls.formatLength(MemManager.getTotal(path));
			String avail = FileUitls.formatLength(MemManager.getAvail(path));
			return "可用:" + avail + "/" + total;
		} else
			return "可用:0B/0B";
	}

	/**
	 * initialize view
	 */
	public void initView() {
		mPgbInner = (ProgressBar) this.findViewById(R.id.pgb_inner_storage);
		mPgbOuter = (ProgressBar) this.findViewById(R.id.pgb_outter_storage);
		mPieView = (PieChartView) this.findViewById(R.id.pie_chart);
		mImgReturn = (ImageView) this.findViewById(R.id.img_title_left);
		mTxtTitle = (TextView) this.findViewById(R.id.txt_title);
		mImgSetting = (ImageView) this.findViewById(R.id.img_title_right);
		mLstSoftTite = (ListView) this.findViewById(R.id.lst_soft_sort);
		mTxtInnerSdMem = (TextView) this
				.findViewById(R.id.txt_inner_mem_summary);
		mTxtOuterSdMem = (TextView) this
				.findViewById(R.id.txt_outer_mem_summary);
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
	 * set widget click event
	 */
	public void initEvent() {
		// left image button click event
		mImgReturn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mLstSoftTite.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Bundle bundle = new Bundle();
				bundle.putInt("position", position);
				String title = mTitles[position];
				bundle.putString("title", title);
				toActivity(SoftDetailActivity.class, bundle);
			}
		});
	}
}
