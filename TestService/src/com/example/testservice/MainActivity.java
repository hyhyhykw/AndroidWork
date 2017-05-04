package com.example.testservice;

import com.example.testservice.MyBroadCastReceiver.OnMsgReceiveListener;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button btn;
	Button btn2;
	MyBroadCastReceiver myBroadCastReceiver;

	Intent service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn = (Button) findViewById(R.id.btn);
		btn2 = (Button) findViewById(R.id.btn2);

		myBroadCastReceiver = new MyBroadCastReceiver();
		IntentFilter filter = new IntentFilter();

		filter.addAction(MyService.ACTION_SEND_PHONE);
		registerReceiver(myBroadCastReceiver, filter);

		service = new Intent(MainActivity.this, MyService.class);
		myBroadCastReceiver
				.setmOnMsgReceiveListener(new OnMsgReceiveListener() {

					@Override
					public void onMsgReceive(String msg) {
						Toast.makeText(MainActivity.this, msg,
								Toast.LENGTH_SHORT).show();
					}
				});

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startService(service);
				btn2.setVisibility(View.VISIBLE);
			}
		});

		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				stopService(service);
				btn2.setVisibility(View.GONE);
			}
		});

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(myBroadCastReceiver);
		if (null != service) {
			stopService(service);
		}
	}
}
