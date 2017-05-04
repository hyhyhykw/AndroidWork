package com.zx.housekeeper;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zx.housekeeper.adapter.TelDetailAdapter;
import com.zx.housekeeper.db.DBWrapper.DBHelper;
import com.zx.housekeeper.entity.TelInfo;

/**
 * 
 * @author HY
 * 
 */
public class TelNumDetailActivity extends BaseActivity {
	/** left image button */
	protected ImageView mImgReturn;
	/** title */
	protected TextView mTxtTitle;
	/** right image button */
	protected ImageView mImgSetting;
	/** telephone number detail list */
	protected ListView mLstTelNum;
	/** telephone number source */
	protected List<TelInfo> telInfos;
	/** alert dialog builder */
	protected AlertDialog.Builder mBuilder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tel_detail);

		Intent intent = this.getIntent();
		Bundle bundle = intent.getBundleExtra("bundle");
		String title = bundle.getString("title");
		int position = bundle.getInt("position");

		initView();
		initTitle(R.drawable.btn_homeasup_default, title, View.INVISIBLE);
		initEvent();

		DBHelper dbHelper = new DBHelper();
		telInfos = dbHelper.readItems(position);
		TelDetailAdapter adapter = new TelDetailAdapter(this, telInfos);
		mLstTelNum.setAdapter(adapter);

	}

	/**
	 * bind widget
	 */
	public void initView() {
		mLstTelNum = (ListView) this.findViewById(R.id.lst_tel_number);
		mImgReturn = (ImageView) this.findViewById(R.id.img_title_left);
		mTxtTitle = (TextView) this.findViewById(R.id.txt_title);
		mImgSetting = (ImageView) this.findViewById(R.id.img_title_right);
	}

	/**
	 * set resource
	 * 
	 * @param resId
	 * @param title
	 * @param visible
	 */
	public void initTitle(int resId, String title, int visible) {
		mImgReturn.setImageResource(resId);
		mTxtTitle.setText(title);
		mImgSetting.setVisibility(visible);
	}

	/**
	 * set widget click event
	 */
	public void initEvent() {
		// left image button click event
		mImgReturn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		// set list item click event
		mLstTelNum.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				showTelDialog(position);
				mBuilder.show();
			}
		});
	}

	public void showTelDialog(final int position) {
		mBuilder = new AlertDialog.Builder(this);
		mBuilder.setTitle("请选择");
		mBuilder.setMessage("号码:" + telInfos.get(position).itemNum);

		DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String number = telInfos.get(position).itemNum;
				switch (which) {
				case AlertDialog.BUTTON_NEGATIVE:
					// the button of cancel click event
					break;
				case AlertDialog.BUTTON_NEUTRAL:
					// the button of neutral click event
					Intent msg = new Intent(Intent.ACTION_SENDTO);
					msg.setData(Uri.parse("smsto:" + number));
					TelNumDetailActivity.this.startActivity(msg);
					break;
				case AlertDialog.BUTTON_POSITIVE:
					// the button of confirm click event
					Intent dial = new Intent(Intent.ACTION_DIAL);
					dial.setData(Uri.parse("tel:" + number));
					TelNumDetailActivity.this.startActivity(dial);
					break;
				}
			}
		};
		mBuilder.setPositiveButton(R.string.dial, listener);
		mBuilder.setNegativeButton(R.string.cancel, listener);
		mBuilder.setNeutralButton(R.string.send_sms, listener);

	}

}
