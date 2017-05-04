package com.zx.housekeeper;

import java.util.List;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zx.housekeeper.adapter.ProcListAdapter;
import com.zx.housekeeper.biz.MemManager;
import com.zx.housekeeper.biz.PhoneDetectionManager;
import com.zx.housekeeper.biz.ProcManager;
import com.zx.housekeeper.constant.Constant;
import com.zx.housekeeper.entity.ProcInfo;

/**
 * speed up page
 * 
 * @author HY
 * 
 */
public class SpeedUpActivity extends BaseActivity {
	/** left image button */
	protected ImageView mImgReturn;

	/** text title */
	protected TextView mTxtTitle;

	/** right image button */
	protected ImageView mImgSetting;

	/** device name */
	protected TextView mTxtDeviceName;

	/** phone model and system version */
	protected TextView mTxtPhoneModel;

	/** show ram used details */
	protected TextView mTxtRamOccupy;

	/** ram used detail */
	protected ProgressBar mPgbRamOccupy;

	/** speed up button */
	protected Button mBtnSpeedUp;

	/** show system progress */
	protected Button mBtnShowSysProgress;

	/**
	 * process flag : 0 system process 1 user process
	 */
	protected int state = Constant.SHOW_USER_PROGRESS;
	/** process manager */
	protected ProcManager mInfoManager;
	/** process info */
	protected List<ProcInfo> procInfos;

	/** handle message from child thread */
	protected Handler mHandler = new Handler();

	/** process list adapter */
	protected ProcListAdapter adapter;
	/** loading image */
	protected ImageView mImgLoad;
	/** loading image animation */
	protected Animation anim;
	/** process list */
	protected ListView mLstProcList;
	/** activity manager */
	protected ActivityManager mActivityManager;
	/** selected all */
	protected CheckBox mChbSelAll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_speed_up);
		initView();
		initData();
		initTitle(R.drawable.btn_homeasup_default, View.INVISIBLE);
		initEvent();
	}

	/**
	 * initialize view
	 */
	public void initView() {
		adapter = new ProcListAdapter(this, procInfos);
		mChbSelAll = (CheckBox) this.findViewById(R.id.chb_kill_all);
		mLstProcList = (ListView) this.findViewById(R.id.lst_progress);
		mImgLoad = (ImageView) this.findViewById(R.id.img_progress_load);
		mBtnShowSysProgress = (Button) this
				.findViewById(R.id.btn_show_sys_progress);
		mBtnSpeedUp = (Button) this.findViewById(R.id.btn_speed_up);
		mPgbRamOccupy = (ProgressBar) this.findViewById(R.id.pgb_ram_occupy);
		mTxtRamOccupy = (TextView) this.findViewById(R.id.txt_ram_occupy);
		mTxtPhoneModel = (TextView) this
				.findViewById(R.id.txt_model_and_version);
		mTxtDeviceName = (TextView) this.findViewById(R.id.txt_device_name);
		mImgReturn = (ImageView) this.findViewById(R.id.img_title_left);
		mTxtTitle = (TextView) this.findViewById(R.id.txt_title);
		mImgSetting = (ImageView) this.findViewById(R.id.img_title_right);
		mActivityManager = (ActivityManager) this
				.getSystemService(Context.ACTIVITY_SERVICE);
		anim = AnimationUtils.loadAnimation(this, R.anim.img_loading_rotate);
		mLstProcList.setAdapter(adapter);
	}

	/**
	 * initialize data
	 */
	public void initData() {
		// set animation
		mImgLoad.setAnimation(anim);
		mImgLoad.setVisibility(View.VISIBLE);
		// set text
		mTxtDeviceName.setText(PhoneDetectionManager.getDeviceName()
				.toUpperCase());
		mTxtPhoneModel.setText(PhoneDetectionManager.getDeviceModel()
				+ " Android" + PhoneDetectionManager.getSystemVersion());
		mTxtRamOccupy.setText("已用内存:" + PhoneDetectionManager.getUsedRam(this)
				+ "/" + PhoneDetectionManager.getAllRam(this));
		// set memory used process
		int progress = (int) (MemManager.getRamPercent(this) * 100);
		mPgbRamOccupy.setProgress(progress);

		mInfoManager = new ProcManager(this);
		new Thread() {
			public void run() {
				mInfoManager.search();
				procInfos = mInfoManager.getProcInfos(state);
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						mImgLoad.setAnimation(null);
						mImgLoad.setVisibility(View.GONE);
						adapter.update(procInfos);
					}
				});
			};
		}.start();

	}

	/**
	 * set widget source
	 * 
	 * @param resId
	 * @param textId
	 * @param vixible
	 */
	public void initTitle(int resId, int visible) {
		String title = this.getIntent().getBundleExtra("bundle")
				.getString("title");
		mImgReturn.setImageResource(resId);

		mTxtTitle.setText(title);
		mImgSetting.setVisibility(visible);
	}

	/**
	 * set widget click event
	 */
	public void initEvent() {
		// set widget click listener
		mImgReturn.setOnClickListener(listener);
		mBtnShowSysProgress.setOnClickListener(listener);
		mBtnSpeedUp.setOnClickListener(listener);
		// process list item click listener
		mLstProcList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ProcInfo pInfo = procInfos.get(position);
				CheckBox chbKillSel = (CheckBox) view
						.findViewById(R.id.chb_select_kill);
				if (pInfo.isChecked()) {
					chbKillSel.setChecked(false);
					pInfo.setChecked(false);
				} else {
					chbKillSel.setChecked(true);
					pInfo.setChecked(true);
				}
			}
		});

		// selected all
		mChbSelAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (null != procInfos) {
					for (ProcInfo pInfo : procInfos) {
						pInfo.setChecked(isChecked);
					}
					adapter.update(procInfos);
				}
			}
		});
	}

	/** widget click listener */
	OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.img_title_left:// left image view click
				finish();
				break;
			case R.id.btn_show_sys_progress:// show system process button
				switch (state) {
				case Constant.SHOW_SYSTEM_PROGRESS:
					mBtnShowSysProgress.setText("显示系统进程");
					state = Constant.SHOW_USER_PROGRESS;
					break;
				case Constant.SHOW_USER_PROGRESS:
					mBtnShowSysProgress.setText("只显示用户进程");
					state = Constant.SHOW_SYSTEM_PROGRESS;
					break;
				}
				procInfos = mInfoManager.getProcInfos(state);
				adapter.update(procInfos);
				break;
			case R.id.btn_speed_up:// kill process button
				for (int i = procInfos.size() - 1; i >= 0; i--) {
					ProcInfo pInfo = procInfos.get(i);
					if (pInfo.isChecked()) {
						mActivityManager.killBackgroundProcesses(pInfo
								.getPkgName());
						procInfos.remove(pInfo);
					}
				}
				mChbSelAll.setChecked(false);
				adapter.update(procInfos);
				break;
			}
		}
	};

}
