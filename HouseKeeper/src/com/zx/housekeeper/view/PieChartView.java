package com.zx.housekeeper.view;

import com.zx.housekeeper.biz.MemManager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * pie shape chart
 * 
 * @author HY
 * 
 */
public class PieChartView extends View {

	/** rectangle graph */
	protected RectF mRectF;
	/** paint */
	protected Paint mPaint;

	protected float mOutterSdSweepAngle = 10;
	protected float mInnerSdSweepAngle = 10;

	public PieChartView(Context context) {
		this(context, null);
	}

	public PieChartView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PieChartView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint.setColor(Color.rgb(255, 140, 0));// orange
		canvas.drawArc(mRectF, 0, 360, true, mPaint);

		mPaint.setColor(Color.rgb(0, 170, 0));// green
		canvas.drawArc(mRectF, -90, mOutterSdSweepAngle, true, mPaint);

		mPaint.setColor(Color.rgb(0, 0, 153));// blue
		canvas.drawArc(mRectF, -90, mInnerSdSweepAngle, true, mPaint);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		mRectF = new RectF(50, 50, width - 50, height - 50);
		setMeasuredDimension(width, height);
	}

	/**
	 * 
	 * @param paths
	 */
	public void setAngle(String[] paths) {
		float outerFinalAngle;
		float innerFinalAngle;
		long outerUsed = 0;
		if (paths.length > 1) {
			outerUsed = MemManager.getUsed(paths[1]);
		}
		long innerUsed = MemManager.getUsed(paths[0]);
		long totalMem = MemManager.getInnerAndOuterTotal(paths[0], paths[1]);
		innerFinalAngle = (innerUsed * 1.0f / totalMem) * 360;
		outerFinalAngle = (outerUsed * 1.0f / totalMem) * 360 + innerFinalAngle;
		// animation
		new PieThread(outerFinalAngle, innerFinalAngle).start();
	}

	/**
	 * 
	 * @author HY
	 * 
	 */
	class PieThread extends Thread {

		/** outer storage final angle */
		float outerFinalAngle;
		/** inner storage final angle */
		float innerFinalAngle;

		public PieThread(float outerFinalAngle, float innerFinalAngle) {
			super();
			this.outerFinalAngle = outerFinalAngle;
			this.innerFinalAngle = innerFinalAngle;
		}

		@Override
		public void run() {
			while (true) {
				if (mInnerSdSweepAngle >= innerFinalAngle) {
					mInnerSdSweepAngle = innerFinalAngle;
				} else {
					mInnerSdSweepAngle += 0.5;
				}
				if (mOutterSdSweepAngle >= outerFinalAngle) {
					mOutterSdSweepAngle = outerFinalAngle;
				} else {
					mOutterSdSweepAngle += 1;
				}

				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				postInvalidate();
				if (mInnerSdSweepAngle >= innerFinalAngle
						&& mOutterSdSweepAngle >= outerFinalAngle) {
					break;
				}
			}
		}
	}

}
