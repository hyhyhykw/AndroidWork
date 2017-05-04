package com.zx.housekeeper;

import java.util.Timer;
import java.util.TimerTask;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * LOGO page
 * 
 * @author HY
 * 
 */
public class LogoActivity extends BaseActivity {
	/** get timer */
	protected Timer mTimer;
	/** run the task after a specified time */
	protected TimerTask mTask = new TimerTask() {
		@Override
		public void run() {
			toActivity(MainActivity.class);
			finish();
			mTimer.cancel();
		}
	};

	/**
	 * logo animation
	 */
	protected ImageView mImgLogo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logo);
		mTimer = new Timer();
		mTimer.schedule(mTask, 2000);
		mImgLogo = (ImageView) this.findViewById(R.id.img_logo);
		mImgLogo.setImageResource(R.drawable.img_logo_anim);
		AnimationDrawable animDraw = (AnimationDrawable) mImgLogo.getDrawable();
		animDraw.setOneShot(true);
		animDraw.start();

	}

}
