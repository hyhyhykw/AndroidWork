package com.zx.housekeeper.view;

import java.util.Timer;
import java.util.TimerTask;

import com.zx.housekeeper.biz.MemManager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * self define circle arc
 * 
 * @author HY
 * 
 */
public class CircleArcView extends View {

	/** rectangle graph */
	protected RectF mRectF;

	/** paint object */
	protected Paint mPaint;

	/** initial angle */
	protected int sweepAngle = 0;

	/** initial state: 0:draw back 1:advance */
	protected int state = 0;

	public CircleArcView(Context context) {
		this(context, null);
	}

	public CircleArcView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CircleArcView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mPaint = new Paint();
		mPaint.setColor(Color.rgb(255, 140, 0));
		mPaint.setAntiAlias(true);
		sweepAngle = calAngle(context);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// get xml file widget size
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		mRectF = new RectF(0, 0, width, height);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawArc(mRectF, -90, sweepAngle, true, mPaint);
	}

	/**
	 * calculate final angle
	 * 
	 * @return
	 */
	public int calAngle(Context context) {
		int angle = 0;
		float memPercent = MemManager.getRamPercent(context);
		angle = (int) (memPercent * 360);
		return angle;
	}

	/**
	 * 
	 * @param finalAngle
	 */
	public void setAngleWithAnim(final Context context) {
		state = 0;
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				switch (state) {
				case 0:
					sweepAngle -= 1;
					if (sweepAngle <= 0) {
						state = 1;
					}
					postInvalidate();
					break;
				case 1:
					sweepAngle += 1;
					int finalAngle = calAngle(context);
					if (sweepAngle >= finalAngle) {
						sweepAngle = finalAngle;
						timer.cancel();
					}
					postInvalidate();
					break;
				}
			}
		}, 10, 2);
	}
}
