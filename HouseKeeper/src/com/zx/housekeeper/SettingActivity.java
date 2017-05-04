package com.zx.housekeeper;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.zx.housekeeper.adapter.SettingAdapter;

/**
 * setting page
 * 
 * @author HY
 * 
 */
public class SettingActivity extends BaseActivity {

	/** title bar left image button */
	protected ImageView mImgReturn;

	/** title */
	protected TextView mTxtTitle;

	/** title bar right image button */
	protected ImageView mImgSetting;

	/** switch list */
	protected ListView mLstSwitch;

	/** switch text source */
	protected String[] setSwitchTxts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		initView();
		initTitle(R.drawable.btn_homeasup_default, R.string.setting_title,
				View.INVISIBLE);
		SettingAdapter adapter = new SettingAdapter(this, setSwitchTxts);
		mLstSwitch.setAdapter(adapter);
		initEvent();
	}

	/**
	 * bind widget
	 */
	public void initView() {
		mLstSwitch = (ListView) this.findViewById(R.id.lst_setting_switch);
		mImgReturn = (ImageView) this.findViewById(R.id.img_title_left);
		mTxtTitle = (TextView) this.findViewById(R.id.txt_title);
		mImgSetting = (ImageView) this.findViewById(R.id.img_title_right);
		setSwitchTxts = this.getResources().getStringArray(
				R.array.texts_setting_switch);
	}

	/**
	 * set widget source
	 * 
	 * @param resId
	 * @param title
	 * @param visible
	 */
	public void initTitle(int resId, int txtId, int visible) {
		mImgReturn.setImageResource(resId);
		mTxtTitle.setText(txtId);
		mImgSetting.setVisibility(visible);
	}

	/**
	 * set click event
	 */
	public void initEvent() {
		// set left button click event
		mImgReturn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// set switch list item click event
		mLstSwitch
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						switch (position) {
						case 0:// self open
						case 1:// push message
						case 2:// notification bar
							CheckBox tgb = (CheckBox) view
									.findViewById(R.id.chb_switch);
							if (tgb.isChecked()) {
								tgb.setChecked(false);
							} else {
								tgb.setChecked(true);
							}
							break;
						case 3:// help
							Bundle bundle = new Bundle();
							bundle.putBoolean("isSetting", true);
							toActivity(LeadActivity.class, bundle);
							break;
						case 4:// idea
							break;
						case 5:// share
							break;
						case 6:// upgrade
							break;
						case 7:// about
							toActivity(AboutActivity.class);
							break;
						}
					}
				});
	}
}
