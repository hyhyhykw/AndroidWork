package com.zx.banner;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * 视图轮播控件
 * 
 * @author HY
 * 
 */
public class BannerLayout extends RelativeLayout {

	// ViewPager
	private ViewPager mViewPager;
	// 线性布局
	private LinearLayout mLinearLayout;
	// 图形指示器
	private List<View> mViews = new ArrayList<View>();

	private List<ImageView> mImageViews = new ArrayList<ImageView>();

	public BannerLayout(Context context) {
		super(context);
		init(context);
	}

	public BannerLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public BannerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	// 设置数据
	public void setmImageViews(List<ImageView> imageViews) {
		this.mImageViews = imageViews;
	}

	// 初始化视图
	private void init(Context context) {
		mViewPager = new ViewPager(context);
		mLinearLayout = new LinearLayout(context);
		for (int i = 0; i < mImageViews.size(); i++) {
			View view = new View(context);
			view.setBackgroundResource(i == 0 ? R.drawable.dots_select
					: R.drawable.dot_normal);
			mViews.add(view);
		}
	}

}
