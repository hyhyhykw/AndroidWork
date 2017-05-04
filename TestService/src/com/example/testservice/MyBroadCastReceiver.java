package com.example.testservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadCastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String msg = intent.getStringExtra(MyService.PHONE);
		if (null != mOnMsgReceiveListener) {
			mOnMsgReceiveListener.onMsgReceive(msg);
		}
	}

	OnMsgReceiveListener mOnMsgReceiveListener;

	public void setmOnMsgReceiveListener(
			OnMsgReceiveListener mOnMsgReceiveListener) {
		this.mOnMsgReceiveListener = mOnMsgReceiveListener;
	}

	public interface OnMsgReceiveListener {
		void onMsgReceive(String msg);
	}
}
