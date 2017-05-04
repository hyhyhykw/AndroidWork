package com.example.chat.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

/**
 * ViewPager的adapter
 * 
 * @author Administrator
 * @create at 2013-5-17
 * @version 1.1.0
 */
public class ViewAdapter extends PagerAdapter {

	// 界面列表
	private List<View> views;

	public ViewAdapter(List<View> views) {
		this.views = views;
	}

	// 销毁position位置的界面
	@Override
	public void destroyItem(View view, int position, Object object) {
		((ViewPager) view).removeView(views.get(position));
	}

	// 获得当前界面数
	@Override
	public int getCount() {
		return null != views ? views.size() : 0;
	}

	// 初始化position位置的界面
	@Override
	public Object instantiateItem(View view, int position) {

		((ViewGroup) view).addView(views.get(position), 0);
		return views.get(position);
	}

	// 判断是否由对象生成界面
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return (view == object);
	}
}