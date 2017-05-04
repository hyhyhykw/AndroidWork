package com.zx.housekeeper.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * lead page ViewPager adapter
 * 
 * @author HY
 * 
 */
public class LeadAdapter extends PagerAdapter {
	/** resource of lead page adapter */
	protected List<ImageView> icons;

	public LeadAdapter() {
	}

	public LeadAdapter(List<ImageView> icons) {
		super();
		this.icons = icons;
	}

	@Override
	public int getCount() {
		return null != icons ? icons.size() : 0;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(icons.get(position));
		return icons.get(position);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(icons.get(position));
	}
}
