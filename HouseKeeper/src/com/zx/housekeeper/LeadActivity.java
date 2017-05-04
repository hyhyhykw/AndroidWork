package com.zx.housekeeper;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.housekeeper.adapter.LeadAdapter;
import com.zx.housekeeper.constant.Constant;

/**
 * lead page
 * 
 * @author HY
 * 
 */
public class LeadActivity extends BaseActivity {



	/** goal widget */
	protected ViewPager mVpager;

	/** text of direct skip */
	protected TextView mTxtLeadSkip;

	/** ViewPager source */
	protected List<ImageView> icons;

	/** three point */
	protected ImageView[] imgPoints;

	/** share file data */
	protected SharedPreferences mSharedPre;

	/** editor object */
	protected Editor mEditor;

	/** judge is or not from setting page */
	protected boolean isSetting = false;
	
	/** picture id */
	protected int[] iconIds = { R.drawable.adware_style_applist,
			R.drawable.adware_style_banner, R.drawable.adware_style_creditswall };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mSharedPre = this.getSharedPreferences(Constant.SHARED_PRE_FIRET,
				Context.MODE_PRIVATE);

		//get default value 
		boolean isFirst = mSharedPre.getBoolean("isFirst", true);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getBundleExtra("bundle");

		if (null != bundle)
			isSetting = bundle.getBoolean("isSetting");
		
		if (isFirst || isSetting) {
			// get editor object 
			mEditor = mSharedPre.edit();
			mEditor.putBoolean("isFirst", false);// set default value false
			mEditor.commit();

			setContentView(R.layout.activity_lead);

			mVpager = (ViewPager) this.findViewById(R.id.viewpager);
			mTxtLeadSkip = (TextView) this.findViewById(R.id.txt_lead_skip);

			// get data
			icons = getData();

			// bind three point
			imgPoints = initView();

			// create adapter object
			LeadAdapter adapter = new LeadAdapter(icons);
			mVpager.setAdapter(adapter);

			//set page change monitor event
			mVpager.setOnPageChangeListener(new OnPageChangeListener() {
				@Override
				public void onPageSelected(int position) {
					mTxtLeadSkip.setVisibility(position == icons.size() - 1 ? View.VISIBLE
							: View.GONE);
					for (int i = 0; i < imgPoints.length; i++) {
						imgPoints[i]
								.setImageResource(i == position ? R.drawable.adware_style_selected
										: R.drawable.adware_style_default);
					}
				}

				@Override
				public void onPageScrolled(int position, float positionOffset,
						int positionOffsetPixels) {
				}

				@Override
				public void onPageScrollStateChanged(int position) {
				}
			});

			// set skip text click event
			mTxtLeadSkip.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (!isSetting) {
						toActivity(LogoActivity.class);
						finish();
					} else {
						finish();
					}

				}
			});
		} else {
			toActivity(LogoActivity.class);
			finish();
		}

	}

	/**
	 * 
	 * @return get ViewPager data
	 */
	public List<ImageView> getData() {
		List<ImageView> icons = new ArrayList<ImageView>();
		for (int i = 0; i < iconIds.length; i++) {
			ImageView img = new ImageView(this);
			img.setImageResource(iconIds[i]);
			icons.add(img);
		}
		return icons;
	}

	/**
	 * 
	 * @return bind three point
	 */
	public ImageView[] initView() {
		ImageView[] points = new ImageView[icons.size()];
		points[0] = (ImageView) this.findViewById(R.id.img_lead_point1);
		points[1] = (ImageView) this.findViewById(R.id.img_lead_point2);
		points[2] = (ImageView) this.findViewById(R.id.img_lead_point3);
		return points;
	}

}
