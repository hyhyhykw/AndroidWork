package com.zx.housekeeper;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * about page
 * 
 * @author HY
 * 
 */
public class AboutActivity extends BaseActivity {

	/** left image button */
	protected ImageView mImgReturn;

	/** text title */
	protected TextView mTxtTitle;

	/** right image button */
	protected ImageView mImgSetting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		initView();
		initTitle(R.drawable.btn_homeasup_default, R.string.about,
				View.INVISIBLE);
		initEvent();
	}

	/**
	 * initialize view
	 */
	public void initView() {
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
	public void initTitle(int resId, int textId, int vixible) {
		mImgReturn.setImageResource(resId);
		mTxtTitle.setText(textId);
		mImgSetting.setVisibility(vixible);
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
	}
}
