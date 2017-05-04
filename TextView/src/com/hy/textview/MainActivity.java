package com.hy.textview;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {

	Button mBtn;
	EditText mEdt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mBtn = (Button) this.findViewById(R.id.btn);
		mEdt = (EditText) this.findViewById(R.id.edt);

		mBtn.setClickable(true);
		mBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						LoadActivity.class);
				if (!mEdt.getText().toString().equals("")) {
					intent.putExtra("txt", mEdt.getText().toString());
				startActivity(intent);
				}				
			}
		});
	}
}
