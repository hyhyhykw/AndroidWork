package com.zx.housekeeper;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.housekeeper.adapter.TelMgrMenuAdapter;
import com.zx.housekeeper.db.DBWrapper;

/**
 * telephone manager main surface
 * 
 * @author HY
 * 
 */
public class TelMgrMainMenuActivity extends BaseActivity {

	/** source */

	protected List<String> telMenuNames;

	/** telephone manager surface menus */
	protected GridView mGrdTelMenu;

	/** left image button */
	protected ImageView mImgReturn;

	/** title text */
	protected TextView mTxtTitle;

	/** right image button */
	protected ImageView mImgSetting;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_tel_mgr);
		DBWrapper.FileHelper fileHelper = new DBWrapper.FileHelper();
		fileHelper.copyFromAssets(this);
		DBWrapper.DBHelper dbHelper = new DBWrapper.DBHelper();
		telMenuNames = dbHelper.readClassList();

		initView();
		initTitle(R.drawable.btn_homeasup_default, View.INVISIBLE);
		initEvent();
	}

	/**
	 * bind widget
	 */
	public void initView() {

		mImgReturn = (ImageView) this.findViewById(R.id.img_title_left);
		mTxtTitle = (TextView) this.findViewById(R.id.txt_title);
		mImgSetting = (ImageView) this.findViewById(R.id.img_title_right);
		mGrdTelMenu = (GridView) this.findViewById(R.id.grd_tel_mgr_main_menu);
		TelMgrMenuAdapter adapter = new TelMgrMenuAdapter(telMenuNames, this);
		mGrdTelMenu.setAdapter(adapter);
	}

	/**
	 * set resource
	 * 
	 * @param resId
	 * @param textId
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
	 * set click event
	 */
	public void initEvent() {
		// set left image button click event
		mImgReturn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// set telephone menu item click event
		mGrdTelMenu.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Bundle bundle = new Bundle();
				bundle.putString("title", telMenuNames.get(position));
				bundle.putInt("position", position);
				toActivity(TelNumDetailActivity.class, bundle);
			}
		});
	}
}
