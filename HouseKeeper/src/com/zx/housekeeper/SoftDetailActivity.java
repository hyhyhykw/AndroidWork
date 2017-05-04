package com.zx.housekeeper;

import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zx.housekeeper.adapter.SoftDetailAdapter;
import com.zx.housekeeper.biz.SoftManager;
import com.zx.housekeeper.entity.SoftInfo;

/**
 * 
 * @author HY
 * 
 */
public class SoftDetailActivity extends BaseActivity {

	/** left image button */
	protected ImageView mImgReturn;

	/** right image button */
	protected ImageView mImgSetting;

	/** text title */
	protected TextView mTxtTitle;

	/** default position */
	protected int position = 3;

	/** soft list */
	protected ListView mLstSoftList;

	/** soft information */
	protected List<SoftInfo> softInfos;

	/** soft manager */
	protected SoftManager softManager;

	/** soft list adapter */
	protected SoftDetailAdapter adapter;

	// /** loading progress */
	// protected ProgressBar mPgbLoad;

	/** loading image */
	protected ImageView mImgLoad;

	/** select all */
	protected CheckBox mChbSelAll;

	/** uninstall button */
	protected Button mBtnDelApp;

	/** loading animation */
	protected Animation anim;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_soft_detail);

		Intent intent = this.getIntent();
		Bundle bundle = intent.getBundleExtra("bundle");
		position = bundle.getInt("position");
		String title = bundle.getString("title");
		mImgLoad = (ImageView) this.findViewById(R.id.img_soft_loading);
		initView();
		initTitle(R.drawable.btn_homeasup_default, title, View.INVISIBLE);
		initEvent();
	}

	@Override
	protected void onResume() {
		super.onResume();
		initData();
	}

	/**
	 * initialize data
	 */
	public void initData() {
		anim = AnimationUtils.loadAnimation(this,
				R.anim.img_loading_rotate);
		mImgLoad.setAnimation(anim);
		mImgLoad.setVisibility(View.VISIBLE);
		softManager = SoftManager.getInstance(this);
		new Thread() {
			public void run() {
				softManager.search();
				softInfos = softManager.getSInfoWithPosition(position);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mImgLoad.setAnimation(null);
						mImgLoad.setVisibility(View.GONE);
						adapter.update(softInfos);
					}
				});
			}
		}.start();
	}

	/**
	 * initialize view
	 */
	public void initView() {
		mBtnDelApp = (Button) this.findViewById(R.id.btn_uninstall_seleted);
		mChbSelAll = (CheckBox) this.findViewById(R.id.chb_uninstall_all);
		mLstSoftList = (ListView) this.findViewById(R.id.lst_soft_list);
		mImgReturn = (ImageView) this.findViewById(R.id.img_title_left);
		mTxtTitle = (TextView) this.findViewById(R.id.txt_title);
		mImgSetting = (ImageView) this.findViewById(R.id.img_title_right);
		adapter = new SoftDetailAdapter(SoftDetailActivity.this, softInfos);
		mLstSoftList.setAdapter(adapter);
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
		// left image button click event
		mImgReturn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		mLstSoftList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				SoftInfo sInfo = softInfos.get(position);
				CheckBox chbUninstall = (CheckBox) view
						.findViewById(R.id.chb_select_unins);
				if (sInfo.isChecked()) {
					sInfo.setChecked(false);
					chbUninstall.setChecked(false);
				} else {
					sInfo.setChecked(true);
					chbUninstall.setChecked(true);
				}
			}
		});
		// select all
		mChbSelAll.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (null != softInfos) {
					for (int i = 0; i < softInfos.size(); i++) {
						SoftInfo sInfo = softInfos.get(i);
						sInfo.setChecked(isChecked);
					}
				}
				adapter.update(softInfos);
			}
		});
		// button uninstall
		mBtnDelApp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				for (int i = 0; i < softInfos.size(); i++) {
					SoftInfo sInfo = softInfos.get(i);
					if (sInfo.isChecked()) {
						if (!sInfo.isUserApp()) {
							Toast.makeText(SoftDetailActivity.this,
									sInfo.getAppName() + "是系统软件，不可以卸载!",
									Toast.LENGTH_SHORT).show();
						} else {
							delApp(sInfo.getPkgName());
						}
					}
				}
				adapter.update(softInfos);
			}
		});
	}

	/**
	 * delete application
	 * 
	 * @param pkgName
	 */
	public void delApp(String pkgName) {
		Intent intent = new Intent(Intent.ACTION_DELETE);
		Uri uninsUri = Uri.parse("package:" + pkgName);
		intent.setData(uninsUri);
		startActivity(intent);
	}
}
