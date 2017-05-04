package com.zx.housekeeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * base activity: provide common attribute
 * 
 * @author HY
 * 
 */
public class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**
	 * skip to other activity
	 * 
	 * @param cla
	 *            activity object of where you will skip
	 */
	public void toActivity(Class<?> cla) {
		toActivity(cla, null);
	}

	/**
	 * 
	 * @param cla
	 *            activity object of where you will skip
	 * @param bundle
	 *            extra data
	 */
	public void toActivity(Class<?> cla, Bundle bundle) {
		Intent intent = new Intent(this, cla);
		if (null != bundle)
			intent.putExtra("bundle", bundle);
		startActivity(intent);
	}
}
