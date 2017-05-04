package com.example.testlistview;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

public class MainActivity extends Activity {

	ListView mLst;
	ArrayAdapter<String> mAdapter;
	List<String> list = new ArrayList<String>();
	private Button mBtn;
	private ProgressBar mPrb;

	Handler mHandler = new Handler();
	private int visibleLastIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mLst = (ListView) findViewById(R.id.lst);

		for (int i = 0; i < 50; i++) {
			list.add("第" + (1 + i) + "条数据");
		}

		mAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);

		mLst.setAdapter(mAdapter);

		// 动态设置ListView高度，使其能嵌套在ScollView中
		// setListHeight(mLst);

		// 设置底部布局，加载更多数据
		// addFooterView();

		// ListView滑动到底部时自动加载数据
		mLst.setOnScrollListener(listener);
	}

	private OnScrollListener listener = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// 滑动状态改变时触发的方法
			if (mAdapter.getCount() - 1 == visibleLastIndex
					&& scrollState == SCROLL_STATE_TOUCH_SCROLL) {
				// mBtn.setVisibility(View.INVISIBLE);
				// mPrb.setVisibility(View.VISIBLE);
				// mHandler.postDelayed(new Runnable() {
				//
				// @Override
				// public void run() {
				//
				// mBtn.setVisibility(View.VISIBLE);
				// mPrb.setVisibility(View.INVISIBLE);
				// }
				// }, 2000);
				loadMore();
				mAdapter.notifyDataSetChanged();
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// 滑动时触发的状态
			visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
		}
	};

	// 添加底部布局
	public void addFooterView() {
		View view = LayoutInflater.from(getApplicationContext()).inflate(
				R.layout.footer_view, null);

		mLst.addFooterView(view);

		mBtn = (Button) view.findViewById(R.id.btn_load);
		mPrb = (ProgressBar) view.findViewById(R.id.prb);

		// mBtn.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// mBtn.setVisibility(View.INVISIBLE);
		// mPrb.setVisibility(View.VISIBLE);
		//
		// mHandler.postDelayed(new Runnable() {
		// @Override
		// public void run() {
		// loadMore();
		// mAdapter.notifyDataSetChanged();
		// mBtn.setVisibility(View.VISIBLE);
		// mPrb.setVisibility(View.INVISIBLE);
		// }
		//
		// }, 2000);
		// }
		// });
	}

	// 加载更多
	private void loadMore() {
		for (int i = 0; i < 5; i++) {
			list.add("第" + (i + 1) + "条数据(新)");
		}
	}

	// 动态设置ListView的高度
	public void setListHeight(ListView lst) {
		ListAdapter adapter = lst.getAdapter();
		if (null == adapter)
			return;

		int totalHeight = 0;
		for (int i = 0; i < adapter.getCount(); i++) {
			View itemView = adapter.getView(i, null, lst);

			// 计算高度，调用绘制方法
			itemView.measure(0, 0);

			totalHeight += itemView.getMeasuredHeight();
		}

		int dividerHeight = lst.getDividerHeight() * (adapter.getCount() - 1);

		// ListView布局参数
		LayoutParams params = lst.getLayoutParams();
		params.height = totalHeight + dividerHeight;
		lst.setLayoutParams(params);
	}

}
