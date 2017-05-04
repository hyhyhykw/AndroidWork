package com.example.testservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

	public static final String ACTION_SEND_PHONE = "action.send.phone";
	public static final String PHONE = "phone";
	String phone = "110";

	@Override
	public void onCreate() {
		Log.e("TAG", "onCreate");
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.e("TAG", "onBind");
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e("TAG", "onStartCommand");
		
		Intent broadCast = new Intent(ACTION_SEND_PHONE);
		broadCast.putExtra(PHONE, phone);
		sendBroadcast(broadCast);
		
		return super.onStartCommand(broadCast, flags, startId);
	}

	@Override
	public void onDestroy() {
		Log.e("TAG", "onDestroy");
		super.onDestroy();
	}
}
