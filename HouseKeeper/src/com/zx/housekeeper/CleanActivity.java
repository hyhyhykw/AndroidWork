package com.zx.housekeeper;

import java.io.File;
import java.util.List;
import java.util.Vector;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.housekeeper.adapter.CleanAdapter;
import com.zx.housekeeper.biz.CleanManager;
import com.zx.housekeeper.biz.FileUitls;
import com.zx.housekeeper.entity.CleanChildInfo;
import com.zx.housekeeper.entity.CleanGroupInfo;

/**
 * 
 * @author HY
 * 
 */
public class CleanActivity extends BaseActivity {
	/** left image button */
	protected ImageView mImgReturn;

	/** right image button */
	protected ImageView mImgSetting;

	/** text title */
	protected TextView mTxtTitle;

	/** list of garbage */
	protected ExpandableListView mElvClean;

	/** group source */
	protected List<CleanGroupInfo> groupInfos;

	/** total garbage has found */
	protected TextView mTxtCleanTotal;

	/** child source */
	protected List<List<CleanChildInfo>> childInfos;

	/** handle the message from child thread */
	protected Handler mHandler = new Handler();

	/** select all */
	protected CheckBox mChbSelectAll;

	/** clean button */
	protected Button mBtnCleanSelected;

	/** clean list adapter */
	protected CleanAdapter adapter;

	// /** resource is loading */
	// protected ProgressDialog mPgb;

	/** image load */
	protected ImageView mImgLoad;

	/** loading animation */
	protected Animation mAnimLoad;
	/** total cache memory */
	protected long sum = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clean);
		// mPgb = ProgressDialog.show(this, "请稍等", "数据正在加载...");

		initView();
		initTitle(R.drawable.btn_homeasup_default, View.INVISIBLE);
		initData();
		initEvent();
	}

	/**
	 * initialize view
	 */
	public void initView() {
		mImgLoad = (ImageView) this.findViewById(R.id.img_clean_load);
		mBtnCleanSelected = (Button) this.findViewById(R.id.btn_clean_seleted);
		mTxtCleanTotal = (TextView) this.findViewById(R.id.txt_clean_total_mem);
		mImgReturn = (ImageView) this.findViewById(R.id.img_title_left);
		mTxtTitle = (TextView) this.findViewById(R.id.txt_title);
		mImgSetting = (ImageView) this.findViewById(R.id.img_title_right);
		mElvClean = (ExpandableListView) this
				.findViewById(R.id.elv_clean_group);

		mChbSelectAll = (CheckBox) this.findViewById(R.id.chb_selete_all);
		groupInfos = new Vector<CleanGroupInfo>();
		String[] cleanLists = CleanActivity.this.getResources().getStringArray(
				R.array.elv_groun_name);
		for (int i = 0; i < cleanLists.length; i++) {
			CleanGroupInfo groupInfo = new CleanGroupInfo();
			groupInfo.setGroupTitle(cleanLists[i]);
			groupInfos.add(groupInfo);
		}
		mAnimLoad = AnimationUtils.loadAnimation(this,
				R.anim.img_loading_rotate);
		mImgLoad.setVisibility(View.VISIBLE);
		mImgLoad.setAnimation(mAnimLoad);

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
	 * 
	 */
	public void initData() {

		new Thread() {
			public void run() {
				CleanManager cleanManager = CleanManager
						.getInstance(CleanActivity.this);
				cleanManager.search();
				List<CleanChildInfo> pChildCacheInfos = cleanManager
						.getPhoneCacheInfos();
				List<CleanChildInfo> pChildFileInfos = cleanManager
						.getPhoneFileInfos();
				List<CleanChildInfo> sdChildCacheInfos = cleanManager
						.getSdCardCacheInfos();
				List<CleanChildInfo> sdChildFileInfos = cleanManager
						.getSdCardFileInfos();

				childInfos = new Vector<List<CleanChildInfo>>();
				childInfos.add(pChildCacheInfos);
				childInfos.add(pChildFileInfos);
				childInfos.add(sdChildCacheInfos);
				childInfos.add(sdChildFileInfos);
				for (int i = 0; i < groupInfos.size(); i++) {
					CleanGroupInfo groupInfo = groupInfos.get(i);
					groupInfo.setGroupTotalMem(calTotal(i));
				}
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						adapter = new CleanAdapter(CleanActivity.this,
								groupInfos, childInfos);
						mElvClean.setAdapter(adapter);
						// mPgb.cancel();
						mImgLoad.setAnimation(null);
						mImgLoad.setVisibility(View.GONE);
						initMem();
					}
				});
			};
		}.start();

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

		// set ExpandableListView child item click event
		mElvClean.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				CheckBox chbSelected = (CheckBox) v
						.findViewById(R.id.chb_selete_clean);
				CleanChildInfo childInfo = childInfos.get(groupPosition).get(
						childPosition);

				if (childInfo.isChecked()) {
					chbSelected.setChecked(false);
					childInfo.setChecked(false);
				} else {
					chbSelected.setChecked(true);
					childInfo.setChecked(true);
				}
				return false;
			}
		});

		// set bottom button click event
		mBtnCleanSelected.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				for (int i = 0; i < childInfos.size(); i++) {
					for (int j = 0; j < childInfos.get(i).size(); j++) {
						CleanChildInfo childInfo = childInfos.get(i).get(j);
						if (childInfo.isChecked()) {
							File delFile = childInfo.getAppFile();
							boolean isSuccess = FileUitls.deleteFile(delFile);
							if (isSuccess) {
								// if success remove item from child list
								childInfos.get(i).remove(j);
								// update group data
								CleanGroupInfo groupInfo = groupInfos.get(i);
								groupInfo.setGroupTotalMem(calTotal(i));
								// update adapter
								adapter.update(groupInfos, childInfos);
								initMem();
							}
						}
					}
				}
			}
		});
		mChbSelectAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (null != childInfos) {
					for (int i = 0; i < childInfos.size(); i++) {
						for (int j = 0; j < childInfos.get(i).size(); j++) {
							CleanChildInfo childInfo = childInfos.get(i).get(j);
							childInfo.setChecked(isChecked);
						}
					}
					adapter.update(groupInfos, childInfos);
				}
			}
		});
	}

	/**
	 * 
	 * @param position
	 * @return
	 */
	public long calTotal(int position) {
		sum = 0;
		List<CleanChildInfo> cleanChildInfos = childInfos.get(position);
		for (int i = 0; i < cleanChildInfos.size(); i++) {
			sum += cleanChildInfos.get(i).getAppTotelMem();
		}
		return sum;
	}

	/**
	 * initial total memory text
	 */
	public void initMem() {
		long totalSum = 0;
		for (int i = 0; i < groupInfos.size(); i++) {
			totalSum += groupInfos.get(i).getGroupTotalMem();
		}
		mTxtCleanTotal.setText(FileUitls.formatLength(totalSum));
	}

}
