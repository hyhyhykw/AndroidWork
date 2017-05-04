package com.zx.housekeeper;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;

import com.zx.housekeeper.adapter.MenuAdapter;
import com.zx.housekeeper.biz.MemManager;
import com.zx.housekeeper.view.CircleArcView;

/**
 * Home page
 * 
 * @author HY
 * 
 */
public class MainActivity extends BaseActivity {

	/** main menu widget */
	protected GridView mGrdMenu;

	/** image source */
	protected int[] menuIcons = { R.drawable.icon_rocket,
			R.drawable.icon_softmgr, R.drawable.icon_phonemgr,
			R.drawable.icon_telmgr, R.drawable.icon_filemgr,
			R.drawable.icon_sdclean };

	/** texts source */
	protected String[] menuTexts;

	/** self defined view */
	protected CircleArcView mArcView;

	/** text of speed up */
	protected TextView mTxtSpeed;

	/** Image of main center */
	protected ImageView mImgCircle;

	/** left image button */
	protected ImageView mImgSetting;

	/** right image button */
	protected ImageView mImgAbout;

	/** event object when you press speed or image */
	protected OnClickListener listener;
	/** ram percent text */
	protected TextView mTxtRamPercent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();
		initEvent();
	}

	/**
	 * initialize view
	 */
	public void initView() {
		mTxtRamPercent = (TextView) this.findViewById(R.id.txt_ram_percent);

		mImgAbout = (ImageView) this.findViewById(R.id.img_title_left);

		mImgSetting = (ImageView) this.findViewById(R.id.img_title_right);

		mGrdMenu = (GridView) this.findViewById(R.id.grd_main_menu);

		mImgCircle = (ImageView) this.findViewById(R.id.img_main_center);

		mArcView = (CircleArcView) this
				.findViewById(R.id.circle_arc_main_center);

		mTxtSpeed = (TextView) this.findViewById(R.id.txt_homeclear_text);

		menuTexts = this.getResources().getStringArray(R.array.text_main_menu);
		MenuAdapter adapter = new MenuAdapter(menuIcons, menuTexts, this);
		mGrdMenu.setAdapter(adapter);

	}

	/**
	 * initialize data
	 */
	public void initData() {
		int ramPercent = (int) (MemManager.getRamPercent(this) * 100);
		mTxtRamPercent.setText("" + ramPercent);
		listener = new SpeedListener();
	}

	/**
	 * set widget click event
	 */
	public void initEvent() {
		// set widget click listener
		mTxtSpeed.setOnClickListener(listener);
		mImgCircle.setOnClickListener(listener);
		mImgAbout.setOnClickListener(listener);
		mImgSetting.setOnClickListener(listener);
		// set GridView click event
		mGrdMenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Bundle bundle = new Bundle();
				bundle.putString("title", menuTexts[position]);
				switch (position) {
				case 0:// speed up
					toActivity(SpeedUpActivity.class, bundle);
					break;
				case 1:// software manage
					toActivity(SoftMgrActivity.class, bundle);
					break;
				case 2:// detection telephone
					toActivity(PhoneDetectionActivity.class, bundle);
					break;
				case 3:// a complete collection of telephone number
					toActivity(TelMgrMainMenuActivity.class, bundle);
					break;
				case 4:// file manager
					toActivity(FileMgrActivity.class, bundle);
					break;
				case 5:// garbage clean
					toActivity(CleanActivity.class, bundle);
					break;
				}
			}
		});
	}

	/** set final angle */
	protected int finalAngle = 0;

	/**
	 * set main center click event
	 * 
	 * @author HY
	 * 
	 */
	class SpeedListener implements OnClickListener {
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.img_title_left:
				toActivity(AboutActivity.class);
				break;
			case R.id.img_title_right:
				toActivity(SettingActivity.class);
				break;
			default:
				mArcView.setAngleWithAnim(MainActivity.this);
				initData();
				break;
			}
		}
	}
}
