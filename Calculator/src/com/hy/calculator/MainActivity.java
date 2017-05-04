package com.hy.calculator;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	protected TextView mTxt;// 显示框
	protected TextView mTxt2;

	boolean pointCount = false;// 小数点数量 false=0,true=1
	boolean isMinus = false;// 判断是否为负数
	boolean isFirst = true;// 判断是第几个数
	double firstNum;
	double secondNum;
	int option;// 运算符状态
	boolean flag = true;// 运算是否正常
	boolean isFinish = true;// 是否计算完成

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/** 绑定id */
		initView();

	}

	private void initView() {
		mTxt = (TextView) this.findViewById(R.id.txt);
		mTxt2 = (TextView) this.findViewById(R.id.txt2);
	}

	public void numBtnClick(View view) {
		String s = mTxt.getText().toString();// 获取文本框显示的字符串
		Button btn = (Button) view;
		double sum = 0;
		int id = btn.getId();
		if (id == R.id.btn_one || id == R.id.btn_two || id == R.id.btn_three
				|| id == R.id.btn_four || id == R.id.btn_five
				|| id == R.id.btn_six || id == R.id.btn_seven
				|| id == R.id.btn_eight || id == R.id.btn_nine
				|| id == R.id.btn_zero || id == R.id.btn_point
				|| id == R.id.btn_minus || id == R.id.btn_percent
				&& pointCount == false) {
			if (option == 0 && isFinish) {
				s = "";
				isFirst = true;
				isFinish = false;
				mTxt.setTextColor(Color.BLACK);
				flag = true;
				mTxt.setText(s);
			}
			if (id == R.id.btn_point) {
				if (null == s || s.equals("")) {
					s += "0" + btn.getText();
				} else {
					s += btn.getText();
				}
				pointCount = true;
			} else if (id == R.id.btn_minus) {
				if (!s.equals("")) {
					if (!isMinus) {
						s = "-" + s;
						isMinus = true;
					} else {
						s = s.substring(1, s.length());
						isMinus = false;
					}
				} else {
					if (!isMinus) {
						s = "-";
						isMinus = true;
					} else {
						s = "";
						isMinus = false;
					}
				}

			} else if (id == R.id.btn_percent) {
				if (!pointCount) {
					s = s.replace(".", "");
				}
				s = "0.0" + s;
				pointCount = false;
			} else {
				s += btn.getText();
			}
			mTxt.setText(s);
		} else if (id == R.id.btn_delete && !s.equals("")) {
			s = s.substring(0, s.length() - 1);
			mTxt.setText(s);
		} else if (id == R.id.btn_clear) {
			s = "";
			mTxt.setText(s);
			mTxt2.setText(s);
			isFirst = true;
			pointCount = false;
			option = 0;
		} else if (isFirst) {
			if (!s.equals("")) {
				firstNum = Double.parseDouble(s);
				s = "";
			} else {
				firstNum = 0;
			}

			pointCount = false;
		} else if (!isFirst && id == R.id.btn_add
				|| id == R.id.btn_subtraction
				|| id == R.id.btn_multiplication
				|| id == R.id.btn_division
				|| id == R.id.btn_equals) {

			if (!s.equals("")) {
				secondNum = Double.parseDouble(s);
			}
			s = "";
			switch (option) {
			case 1:
				sum = firstNum + secondNum;
				firstNum = sum;
				break;
			case 2:
				sum = firstNum - secondNum;
				firstNum = sum;
				break;
			case 3:
				sum = firstNum * secondNum;
				firstNum = sum;

				break;
			case 4:
				if (secondNum != 0) {
					sum = firstNum / secondNum;
					firstNum = sum;
				} else {
					flag = false;
				}
				break;
			}
			if (!flag) {
				s = "Error";
				mTxt.setTextColor(Color.RED);
				mTxt.setText(s);
			} else {
				s = String.valueOf(sum);
			}
			mTxt.setText(s);
			option = 0;
			isFinish = true;
			s = "";
		}
		
		int key = btn.getId();
		
		switch (key) {
		case R.id.btn_add:
			isFirst = false;
			option = 1;
			s = "";
			pointCount = false;
			mTxt.setText(s);
			mTxt2.setText(String.valueOf(firstNum));
			break;
		case R.id.btn_subtraction:
			isFirst = false;
			option = 2;
			s = "";
			pointCount = false;
			mTxt.setText(s);
			mTxt2.setText(String.valueOf(firstNum));
			break;
		case R.id.btn_multiplication:
			isFirst = false;
			option = 3;
			s = "";
			pointCount = false;
			mTxt.setText(s);
			mTxt2.setText(String.valueOf(firstNum));
			break;
		case R.id.btn_division:
			isFirst = false;
			option = 4;
			s = "";
			pointCount = false;
			mTxt.setText(s);
			mTxt2.setText(String.valueOf(firstNum));
			break;
		}
	}

}
