package com.zx.housekeeper;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zx.housekeeper.adapter.PhoneInfoAdapter;
import com.zx.housekeeper.biz.PhoneDetectionManager;
import com.zx.housekeeper.receiver.BatteryBroadcastReceiver;
import com.zx.housekeeper.receiver.BatteryBroadcastReceiver.OnReceiveBatteryListener;

public class PhoneDetectionActivity extends BaseActivity implements
		OnReceiveBatteryListener {
	/** left image button */
	protected ImageView mImgReturn;

	/** text title */
	protected TextView mTxtTitle;

	/** right image button */
	protected ImageView mImgSetting;

	/** phone info list adapter */
	protected PhoneInfoAdapter adapter;

	/** phone information image resource */
	protected int[] mImgIcons = { R.drawable.setting_info_icon_version,
			R.drawable.setting_info_icon_space,
			R.drawable.setting_info_icon_cpu,
			R.drawable.setting_info_icon_camera,
			R.drawable.setting_info_icon_root,
			R.drawable.setting_info_icon_bluetooth };

	/** phone information image background resource */
	protected int[] mImgIconBgs = {
			R.drawable.notification_information_progress_green,
			R.drawable.notification_information_progress_red,
			R.drawable.notification_information_progress_yellow };

	/** phone informations */
	protected String[][] phoneInfos;

	/** phone info list */
	protected ListView mLstPhoneInfo;

	/** display battery information */
	protected ProgressBar mPgbBattery;

	/** receive battery charge */
	protected BatteryBroadcastReceiver mbbReceiver;

	/** battery charge percent */
	protected TextView mTxtBatteryPercent;

	protected AlertDialog.Builder builder;

	protected Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			phoneInfos = PhoneDetectionManager
					.getPhoneInfos(PhoneDetectionActivity.this);
			adapter.update(mImgIcons, mImgIconBgs, phoneInfos);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_detection);
		initView();

		initData();
		initTitle(R.drawable.btn_homeasup_default, View.INVISIBLE);
		initEvent();
	}

	@Override
	protected void onStop() {
		super.onStop();
		// unregister receiver
		unregisterReceiver(mbbReceiver);
	}

	/**
	 * initialize data
	 */
	public void initData() {

		mbbReceiver = new BatteryBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_BATTERY_CHANGED);
		// register broadcast receiver
		registerReceiver(mbbReceiver, filter);
		mbbReceiver.setOnReceiveBatteryListener(this);

		phoneInfos = PhoneDetectionManager.getPhoneInfos(this);
		// phone info adapter
		adapter = new PhoneInfoAdapter(this, mImgIcons, mImgIconBgs, phoneInfos);
		mLstPhoneInfo.setAdapter(adapter);

		builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.battery_charge_info);

	}

	/**
	 * initialize view
	 */
	public void initView() {
		mTxtBatteryPercent = (TextView) this
				.findViewById(R.id.txt_battery_percent);
		mPgbBattery = (ProgressBar) this.findViewById(R.id.pgb_battery_info);
		mLstPhoneInfo = (ListView) this.findViewById(R.id.lst_phone_info);
		mImgReturn = (ImageView) this.findViewById(R.id.img_title_left);
		mTxtTitle = (TextView) this.findViewById(R.id.txt_title);
		mImgSetting = (ImageView) this.findViewById(R.id.img_title_right);
	}

	/**
	 * set widget source
	 * 
	 * @param resId
	 * @param textId
	 * @param vixible
	 */
	public void initTitle(int resId, int vizible) {
		mImgReturn.setImageResource(resId);
		String title = this.getIntent().getBundleExtra("bundle")
				.getString("title");
		mTxtTitle.setText(title);
		mImgSetting.setVisibility(vizible);
	}

	/**
	 * set widget click event
	 */
	public void initEvent() {
		mImgReturn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mLstPhoneInfo.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 5) {
					PhoneDetectionManager.setBluetoothState();
				}
			}
		});
	}

	/**
	 * battery icon click event
	 * 
	 * @param view
	 */
	public void batteryClick(View view) {
		builder.show();
	}

	@Override
	public void receiveCurrentBattery(int currentBattery, int batteryTemp) {
		mPgbBattery.setProgress(currentBattery);
		mTxtBatteryPercent.setText("" + currentBattery + "%");
		String[] items = { "当前电量：" + currentBattery, "电池温度：" + batteryTemp };
		builder.setItems(items, null);
	}
}
