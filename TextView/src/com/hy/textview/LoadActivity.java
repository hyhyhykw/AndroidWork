package com.hy.textview;

import android.os.Bundle;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

public class LoadActivity extends Activity {

	TextView mTxt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load);
		mTxt=(TextView) this.findViewById(R.id.txt);
		Intent intent =this.getIntent();
		mTxt.setText(intent.getStringExtra("txt"));
		
	}

	

}
